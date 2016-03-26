package cn.chenzhongjin.greendao.sample.ui.fragment.delete;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.chenzhongjin.greendao.sample.R;
import cn.chenzhongjin.greendao.sample.entity.User;
import cn.chenzhongjin.greendao.sample.listeners.CustomItemClickListener;
import cn.chenzhongjin.greendao.sample.ui.activity.main.adapter.UserAdapter;
import cn.chenzhongjin.greendao.sample.ui.base.BaseFragment;

/**
 * @author chenzj
 * @Title: DeleteFragment
 * @Description: 类的描述 -
 * @date 2016/3/26 10:51
 * @email admin@chenzhongjin.cn
 */
public class DeleteFragment extends BaseFragment {

    private static final String TAG = DeleteFragment.class.getSimpleName();

    private List<User> mUserList;
    private UserAdapter mAdapter;

    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_delete;

    }

    @Override
    protected void initViews(View view) {
        ButterKnife.bind(this, view);
        mUserList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            User user = new User();
            mUserList.add(user);
        }
        mAdapter = new UserAdapter(mUserList, new CustomItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Logger.t(TAG).i("itemClick:viewId=" + view.getId() + "  position=" + position);
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

}

