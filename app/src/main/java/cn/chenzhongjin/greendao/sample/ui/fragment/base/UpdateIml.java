package cn.chenzhongjin.greendao.sample.ui.fragment.base;

import android.view.View;

import com.dreamliner.lib.frame.base.BaseCompatActivity;

import cn.chenzhongjin.greendao.sample.database.User;
import cn.chenzhongjin.greendao.sample.ui.fragment.DaoOperationFragment;
import cn.chenzhongjin.greendao.sample.ui.fragment.OperationFragment;

import static cn.chenzhongjin.greendao.sample.ui.fragment.DaoOperationFragment.DAO_OPERATION_UPDATE_TYPE;

public class UpdateIml extends BaseDaoOperation {

    public UpdateIml(BaseCompatActivity activity, DaoOperationFragment fragment) {
        super(activity, fragment);
    }

    @Override
    public void onItemClick(View v, int position, User user) {
        mFragment.startDialog(OperationFragment.newInstance(DAO_OPERATION_UPDATE_TYPE, user));
    }
}
