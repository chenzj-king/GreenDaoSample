package cn.chenzhongjin.greendao.sample.ui.activity.main;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.chenzhongjin.greendao.sample.R;
import cn.chenzhongjin.greendao.sample.entity.User;
import cn.chenzhongjin.greendao.sample.listeners.CustomItemClickListener;
import cn.chenzhongjin.greendao.sample.ui.activity.main.adapter.UserAdapter;
import cn.chenzhongjin.greendao.sample.ui.base.BaseRvActivity;

/**
 * @author: chenzj
 * @Title: MainActivity
 * @Description:
 * @date: 2016/3/24 22:45
 * @email: admin@chenzhongjin.cn
 */
public class MainActivity extends BaseRvActivity implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Bind(R.id.common_toolbar)
    Toolbar mToolbar;
    @Bind(R.id.toolbar_title_textview)
    TextView mTitleTextView;

    private List<User> mUserList;
    private UserAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(this);
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);

        mUserList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            User user = new User();
            mUserList.add(user);
        }
        Logger.i("userList size=" + mUserList.size());
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

    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                mAdapter.notifyDataSetChanged();
            }
        }, 1500);
    }
}
