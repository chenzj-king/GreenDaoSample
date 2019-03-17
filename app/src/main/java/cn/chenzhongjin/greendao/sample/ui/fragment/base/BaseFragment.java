package cn.chenzhongjin.greendao.sample.ui.fragment.base;

import android.view.View;

import com.dreamliner.lib.frame.base.BaseCompatFragment;

import cn.chenzhongjin.greendao.sample.R;
import cn.chenzhongjin.greendao.sample.database.UserDao;
import cn.chenzhongjin.greendao.sample.utils.DaoUtil;
import me.yokeyword.fragmentation.ISupportFragment;

import static cn.chenzhongjin.greendao.sample.ui.fragment.DaoOperationFragment.BD_DAO_OPERATION_TYPE_KEY;

public abstract class BaseFragment extends BaseCompatFragment {

    protected int mType;
    public UserDao mUserDao;

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void initSpecialView(View view) {
        super.initSpecialView(view);
        mType = getArguments().getInt(BD_DAO_OPERATION_TYPE_KEY);
        mUserDao = DaoUtil.INSTANCE.getDaoSession().getUserDao();
    }

    protected void startDialog(ISupportFragment supportFragment) {
        mContext.extraTransaction()
                .setCustomAnimations(R.anim.v_fragment_pop_enter, R.anim.v_fragment_pop_exit)
                .startDontHideSelf(supportFragment);
    }
}
