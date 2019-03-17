package cn.chenzhongjin.greendao.sample.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dreamliner.lib.util.ValidateUtil;
import com.flyco.tablayout.listener.OnTabSelectListener;

import org.greenrobot.greendao.query.WhereCondition;

import java.util.ArrayList;
import java.util.List;

import cn.chenzhongjin.greendao.sample.R;
import cn.chenzhongjin.greendao.sample.database.User;
import cn.chenzhongjin.greendao.sample.database.UserDao;
import cn.chenzhongjin.greendao.sample.database.UserDao.Properties;
import cn.chenzhongjin.greendao.sample.databinding.FraOperationBinding;
import cn.chenzhongjin.greendao.sample.entity.OperationUser;
import cn.chenzhongjin.greendao.sample.eventbus.SelectEvent;
import cn.chenzhongjin.greendao.sample.eventbus.UserChangeEvent;
import cn.chenzhongjin.greendao.sample.ui.fragment.base.BaseFragment;

import static cn.chenzhongjin.greendao.sample.ui.fragment.DaoOperationFragment.BD_DAO_OPERATION_TYPE_KEY;
import static cn.chenzhongjin.greendao.sample.ui.fragment.DaoOperationFragment.DAO_OPERATION_INSERT_TYPE;
import static cn.chenzhongjin.greendao.sample.ui.fragment.DaoOperationFragment.DAO_OPERATION_SELECT_TYPE;
import static cn.chenzhongjin.greendao.sample.ui.fragment.DaoOperationFragment.DAO_OPERATION_UPDATE_TYPE;

public class OperationFragment extends BaseFragment {

    private FraOperationBinding mBinding;

    private OperationUser mOperationUser = new OperationUser();

    public static final String BD_USER_KEY = "user";

    private User mUser;

    public static OperationFragment newInstance(int type) {
        return newInstance(type, null);
    }

    public static OperationFragment newInstance(int type, User user) {
        Bundle args = new Bundle();
        args.putInt(BD_DAO_OPERATION_TYPE_KEY, type);
        args.putSerializable(BD_USER_KEY, user);
        OperationFragment fragment = new OperationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = FraOperationBinding.inflate(inflater, container, false);
        view = mBinding.getRoot();
        return view;
    }

    @Override
    protected void initViews(View view) {
        mBinding.setFra(this);

        mUser = (User) getArguments().getSerializable(BD_USER_KEY);
        if (null != mUser) {
            mOperationUser = new OperationUser(mUser.getName(), mUser.getSex(), mUser.getPhone());
        } else {
            mOperationUser.setSex("男");
        }
        mBinding.setUser(mOperationUser);

        mBinding.segmentTabLayout.setTabData(new String[]{"男", "女"});
        mBinding.segmentTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mOperationUser.setSex(position == 0 ? "男" : "女");
            }

            @Override
            public void onTabReselect(int position) {
            }
        });
        if (!TextUtils.isEmpty(mOperationUser.getSex())) {
            mBinding.segmentTabLayout.setCurrentTab(mOperationUser.getSex().equals("男") ? 0 : 1);
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel_bt:
                pop();
                break;
            case R.id.sure_bt:
                switch (mType) {
                    case DAO_OPERATION_INSERT_TYPE:
                    case DAO_OPERATION_UPDATE_TYPE:
                        insertOrUpdate();
                        break;
                    case DAO_OPERATION_SELECT_TYPE:
                        select();
                        break;
                }
                break;
        }
    }

    private void insertOrUpdate() {
        if (TextUtils.isEmpty(mOperationUser.getName())) {
            showToast("请输入姓名");
            return;
        }
        if (TextUtils.isEmpty(mOperationUser.getPhone()) || !ValidateUtil.isValidate(ValidateUtil.PHONE_NUM, mOperationUser.getPhone())) {
            showToast("请输入正确的手机号码");
            return;
        }
        if (TextUtils.isEmpty(mOperationUser.getSex())) {
            showToast("请选择性别");
            return;
        }
        if (null == mUser) {
            mUser = new User();
        }
        mUser.setName(mOperationUser.getName());
        mUser.setSex(mOperationUser.getSex());
        mUser.setPhone(mOperationUser.getPhone());
        mUser.setTime(System.currentTimeMillis());
        mUserDao.insertOrReplace(mUser);
        postEvent(new UserChangeEvent());
        pop();
    }

    private String getContent(String content) {
        if (TextUtils.isEmpty(content)) {
            return "";
        }
        return content;
    }

    private void select() {
        List<WhereCondition> whereConditionList = new ArrayList<>();

        //模糊匹配姓名
        whereConditionList.add(Properties.Name.like("%" + getContent(mOperationUser.getName()) + "%"));
        //模糊匹配电话号码
        whereConditionList.add(Properties.Phone.like("%" + getContent(mOperationUser.getPhone()) + "%"));
        //精确匹配男/女
        if (!TextUtils.isEmpty(mOperationUser.getSex())) {
            whereConditionList.add(UserDao.Properties.Sex.eq(getContent(mOperationUser.getSex())));
        }
        postEvent(new SelectEvent(mUserDao.queryBuilder().where(Properties.Time.isNotNull(),
                whereConditionList.toArray(new WhereCondition[]{})).list()));
        pop();
    }
}
