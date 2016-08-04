package cn.chenzhongjin.greendao.sample.ui.fragment.insert;

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

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.chenzhongjin.greendao.sample.AppContext;
import cn.chenzhongjin.greendao.sample.R;
import cn.chenzhongjin.greendao.sample.database.User;
import cn.chenzhongjin.greendao.sample.database.UserDao;
import cn.chenzhongjin.greendao.sample.event.UserDataEvent;
import cn.chenzhongjin.greendao.sample.listeners.CustomItemClickListener;
import cn.chenzhongjin.greendao.sample.ui.activity.main.adapter.UserAdapter;
import cn.chenzhongjin.greendao.sample.ui.base.BaseRvFragment;
import cn.chenzhongjin.greendao.sample.utils.ValidateUtils;
import info.hoang8f.android.segmented.SegmentedGroup;

/**
 * @author chenzj
 * @Title: InsertFragment
 * @Description: 类的描述 -
 * @date 2016/3/26 11:12
 * @email admin@chenzhongjin.cn
 */
public class InsertFragment extends BaseRvFragment {

    private static final String TAG = InsertFragment.class.getSimpleName();

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
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_insert;

    }

    @Override
    protected void initViews(View view) {
        ButterKnife.bind(this, view);

        //init cache data
        mUserDao = AppContext.getInstance().getDaoSession().getUserDao();
        List<User> mDaoUserList = mUserDao.loadAll();
        mUserList = null == mDaoUserList ? new ArrayList<User>() : mDaoUserList;
        mAdapter = new UserAdapter(mUserList, new CustomItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Logger.t(TAG).i("itemClick:viewId=" + view.getId() + "  position=" + position);
            }
        });
        mRecycler.setAdapter(mAdapter);
        mRecycler.setRefreshListener(this);
        mAdapter.notifyDataSetChanged();

    }


    @OnClick(R.id.select_userinfo_button)
    void clickInsertButton() {
        MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
                .title("新增用户数据")
                .customView(R.layout.dialog_insert_customview, true)
                .positiveText("新增")
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
                        String sexStr = mSegmentedGroup.getCheckedRadioButtonId() == R.id.radio_button_man ? "男" : "女";

                        Logger.t(TAG).i("nameStr=" + nameStr + "\nphoneNumStr=" + phoneNumStr + "\nsexStr=" + sexStr);

                        if (TextUtils.isEmpty(nameStr)) {
                            mNameEt.setError("姓名不能为空");
                            return;
                        }
                        if (TextUtils.isEmpty(phoneNumStr)) {
                            mPhoneNumEt.setError("手机号码不能为空");
                            return;
                        } else if (!ValidateUtils.isPhoneNumber(phoneNumStr)) {
                            mPhoneNumEt.setError("请输入正确的手机号码");
                            return;
                        }

                        //insert to database
                        User user = new User();
                        user.setName(nameStr);
                        user.setPhoneNumber(Long.parseLong(phoneNumStr));
                        user.setSex(sexStr);
                        user.setUpdateTime(System.currentTimeMillis());
                        mUserDao.insert(user);

                        //refresh ui
                        mAdapter.insert(user, 0);
                        postEvent(new UserDataEvent(mUserDao.loadAll()));
                        dialog.dismiss();

                    }
                }).build();

        mNameEt = (EditText) dialog.getCustomView().findViewById(R.id.name);
        mPhoneNumEt = (EditText) dialog.getCustomView().findViewById(R.id.phone_number);
        mSegmentedGroup = (SegmentedGroup) dialog.getCustomView().findViewById(R.id.segmented);
        mSegmentedGroup.check(R.id.radio_button_man);
        dialog.show();
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

