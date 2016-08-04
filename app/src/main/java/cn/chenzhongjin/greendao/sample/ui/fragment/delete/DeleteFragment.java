package cn.chenzhongjin.greendao.sample.ui.fragment.delete;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.daimajia.swipe.util.Attributes;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.ButterKnife;
import cn.chenzhongjin.greendao.sample.AppContext;
import cn.chenzhongjin.greendao.sample.R;
import cn.chenzhongjin.greendao.sample.database.User;
import cn.chenzhongjin.greendao.sample.database.UserDao;
import cn.chenzhongjin.greendao.sample.event.UserDataEvent;
import cn.chenzhongjin.greendao.sample.listeners.CustomItemClickListener;
import cn.chenzhongjin.greendao.sample.ui.base.BaseRvFragment;
import cn.chenzhongjin.greendao.sample.ui.fragment.delete.adapter.UserDeleteAdapter;

/**
 * @author chenzj
 * @Title: DeleteFragment
 * @Description: 类的描述 -
 * @date 2016/3/26 10:51
 * @email admin@chenzhongjin.cn
 */
public class DeleteFragment extends BaseRvFragment {

    private static final String TAG = DeleteFragment.class.getSimpleName();

    private UserDeleteAdapter mAdapter;
    private UserDao mUserDao;
    private List<User> mUserList;

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
        return R.layout.fragment_delete;

    }

    @Override
    protected void initViews(View view) {
        ButterKnife.bind(this, view);
        //init cache data
        mUserDao = AppContext.getInstance().getDaoSession().getUserDao();
        mUserList = mUserDao.loadAll();

        mAdapter = new UserDeleteAdapter(mUserList, new CustomItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                if (view.getId() == R.id.trash) {

                    getBaseAct().showBaseNotitleDialog("是否删除此条数据", getString(android.R.string.ok),
                            getString(android.R.string.cancel), new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    if (which == DialogAction.POSITIVE) {
                                        //del the data
                                        User user = mAdapter.getItemData(position);
                                        mUserDao.delete(user);
                                        mAdapter.remove(position);

                                        postEvent(new UserDataEvent(mUserDao.loadAll()));

                                        dialog.dismiss();
                                    }
                                }
                            });
                }
            }
        });
        ((RecyclerSwipeAdapter) mAdapter).setMode(Attributes.Mode.Single);
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

    void loadLatestData(List<User> userList) {

        mAdapter.clear();
        mAdapter.addAll(userList);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    void loadLatestData(UserDataEvent userDataEvent) {
        Logger.t(TAG).i("revice loadLatestData event");
        loadLatestData(userDataEvent.getUsers());
    }
}

