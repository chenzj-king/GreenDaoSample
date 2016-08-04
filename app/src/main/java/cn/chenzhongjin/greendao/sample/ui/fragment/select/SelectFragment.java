package cn.chenzhongjin.greendao.sample.ui.fragment.select;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.chenzhongjin.greendao.sample.AppContext;
import cn.chenzhongjin.greendao.sample.R;
import cn.chenzhongjin.greendao.sample.database.User;
import cn.chenzhongjin.greendao.sample.database.UserDao;
import cn.chenzhongjin.greendao.sample.event.UserDataEvent;
import cn.chenzhongjin.greendao.sample.ui.activity.main.adapter.UserAdapter;
import cn.chenzhongjin.greendao.sample.ui.base.BaseRvFragment;
import info.hoang8f.android.segmented.SegmentedGroup;

/**
 * @author chenzj
 * @Title: SelectFragment
 * @Description: 类的描述 -
 * @date 2016/3/26 11:12
 * @email admin@chenzhongjin.cn
 */
public class SelectFragment extends BaseRvFragment {

    private static final String TAG = SelectFragment.class.getSimpleName();

    private List<User> mUserList;
    private UserAdapter mAdapter;
    private UserDao mUserDao;

    private EditText mNameEt;
    private EditText mPhoneNumEt;
    private SegmentedGroup mSegmentedGroup;

    @Override
    protected boolean isRegisterEvent() {
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_select;

    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }

    @Override
    protected void initViews(View view) {
        ButterKnife.bind(this, view);

        //init cache data
        mUserDao = AppContext.getInstance().getDaoSession().getUserDao();
        mUserList = mUserDao.loadAll();
        mAdapter = new UserAdapter(mUserList, null);
        mRecycler.setAdapter(mAdapter);
        mRecycler.setRefreshListener(this);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        getHandler().post(new Runnable() {
            @Override
            public void run() {
                loadLatestData(mUserDao.loadAll());
            }
        });
    }

    @OnClick(R.id.select_userinfo_button)
    void clickSelectButton() {
        MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
                .title("查询用户数据")
                .customView(R.layout.dialog_insert_customview, true)
                .positiveText("查询")
                .negativeText(android.R.string.cancel)
                .autoDismiss(false)
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        String nameStr = mNameEt.getText().toString();
                        String phoneNumStr = mPhoneNumEt.getText().toString();
                        String sexStr = "";
                        if (mSegmentedGroup.getCheckedRadioButtonId() == R.id.radio_button_man) {
                            sexStr = "男";
                        } else if (mSegmentedGroup.getCheckedRadioButtonId() == R.id.radio_button_woman) {
                            sexStr = "女";
                        }
                        selectData(nameStr, phoneNumStr, sexStr, dialog);
                    }
                }).build();

        mNameEt = (EditText) dialog.getCustomView().findViewById(R.id.name);
        mPhoneNumEt = (EditText) dialog.getCustomView().findViewById(R.id.phone_number);
        mSegmentedGroup = (SegmentedGroup) dialog.getCustomView().findViewById(R.id.segmented);

        dialog.show();
    }

    void selectData(String name, String phoneNum, String sex, final MaterialDialog materialDialog) {

        Logger.t(TAG).i("name=" + name + "\nphoneNum=" + phoneNum + "\nsex=" + sex);
        WhereCondition nameWhereCondition = UserDao.Properties.Name.like("%" + name + "%");
        WhereCondition phoneNumWhereCondition = UserDao.Properties.PhoneNumber.like("%" + phoneNum + "%");
        WhereCondition sexWhereCondition = UserDao.Properties.Sex.eq(sex);

        List<User> users;

        if (TextUtils.isEmpty(sex)) {
            users = mUserDao.queryBuilder().where(nameWhereCondition, phoneNumWhereCondition).list();
        } else {
            users = mUserDao.queryBuilder().where(nameWhereCondition, phoneNumWhereCondition, sexWhereCondition).list();
        }

        loadLatestData(users);
        materialDialog.dismiss();
    }

    void loadLatestData(List<User> users) {
        mAdapter.clear();
        mAdapter.addAll(users);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    void loadLatestData(UserDataEvent userDataEvent) {
        Logger.t(TAG).i("revice loadLatestData event");
        loadLatestData(userDataEvent.getUsers());
    }
}

