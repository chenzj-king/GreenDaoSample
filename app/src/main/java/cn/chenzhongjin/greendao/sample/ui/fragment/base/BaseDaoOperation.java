package cn.chenzhongjin.greendao.sample.ui.fragment.base;

import android.view.View;

import com.dreamliner.lib.frame.base.BaseCompatActivity;
import com.dreamliner.rvhelper.interfaces.OnItemClickListener;
import com.dreamliner.rvhelper.interfaces.OnItemLongClickListener;

import cn.chenzhongjin.greendao.sample.database.User;
import cn.chenzhongjin.greendao.sample.ui.fragment.DaoOperationFragment;

import static cn.chenzhongjin.greendao.sample.ui.fragment.DaoOperationFragment.DAO_OPERATION_DELETE_TYPE;
import static cn.chenzhongjin.greendao.sample.ui.fragment.DaoOperationFragment.DAO_OPERATION_UPDATE_TYPE;


public class BaseDaoOperation implements OnItemClickListener<User>, OnItemLongClickListener<User> {

    protected BaseCompatActivity mActivity;
    protected DaoOperationFragment mFragment;

    public BaseDaoOperation(BaseCompatActivity activity, DaoOperationFragment fragment) {
        mActivity = activity;
        mFragment = fragment;
    }

    public static BaseDaoOperation getDaoOperationByType(BaseCompatActivity activity, DaoOperationFragment daoOperationFragment
            , int type) {
        switch (type) {
            case DAO_OPERATION_DELETE_TYPE:
                return new DeleteIml(activity, daoOperationFragment);
            case DAO_OPERATION_UPDATE_TYPE:
                return new UpdateIml(activity, daoOperationFragment);
        }
        return new BaseDaoOperation(activity, daoOperationFragment);
    }

    @Override
    public void onItemClick(View v, int position, User user) {
    }

    @Override
    public boolean onItemLongClick(View v, int position, User user) {
        return false;
    }
}
