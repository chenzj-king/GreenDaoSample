package cn.chenzhongjin.greendao.sample.ui.fragment.base;

import android.view.View;

import com.dreamliner.lib.customdialog.CustomDialogAction;
import com.dreamliner.lib.frame.base.BaseCompatActivity;

import cn.chenzhongjin.greendao.sample.database.User;
import cn.chenzhongjin.greendao.sample.eventbus.UserChangeEvent;
import cn.chenzhongjin.greendao.sample.ui.fragment.DaoOperationFragment;

public class DeleteIml extends BaseDaoOperation {

    public DeleteIml(BaseCompatActivity activity, DaoOperationFragment fragment) {
        super(activity, fragment);
    }

    @Override
    public boolean onItemLongClick(View v, int position, User user) {
        mActivity.showBaseDialog("确定删除这条记录？", "确认", "取消", (dialog, which) -> {
            dialog.dismiss();
            if (which == CustomDialogAction.POSITIVE) {
                mFragment.mUserDao.delete(user);
                mFragment.postEvent(new UserChangeEvent());
            }
        });
        return true;
    }
}
