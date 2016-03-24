package cn.chenzhongjin.greendao.sample.ui.base;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.malinskiy.superrecyclerview.swipe.SparseItemRemoveAnimator;

import cn.chenzhongjin.greendao.sample.R;

/**
 * @author: chenzj
 * @Title: BaseRvActivity
 * @Description:
 * @date: 2016/3/24 23:35
 * @email: admin@chenzhongjin.cn
 */
public abstract class BaseRvActivity extends BaseActivity {

    protected abstract RecyclerView.LayoutManager getLayoutManager();

    protected boolean isRecyclerMove = false;
    protected int recyclerIndex = 0;

    protected SuperRecyclerView mRecycler;
    protected RecyclerView mRecyclerView;

    protected SparseItemRemoveAnimator mSparseAnimator;
    protected RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void initSpecialView() {
        // init recycler deafult config
        mRecycler = (SuperRecyclerView) findViewById(R.id.list);
        mRecyclerView = mRecycler != null ? mRecycler.getRecyclerView() : null;
        mRecyclerView.addOnScrollListener(new RecyclerViewListener());

        mLayoutManager = getLayoutManager();
        mRecycler.setLayoutManager(mLayoutManager);
        mRecycler.setRefreshingColorResources(android.R.color.holo_orange_light, android.R.color.holo_blue_light,
                android.R.color.holo_green_light, android.R.color.holo_red_light);
    }

    class RecyclerViewListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);

            if (isRecyclerMove && newState == RecyclerView.SCROLL_STATE_IDLE) {
                isRecyclerMove = false;
                int n = recyclerIndex - ((LinearLayoutManager) mLayoutManager).findFirstVisibleItemPosition();
                if (0 <= n && n < mRecyclerView.getChildCount()) {
                    int top = mRecyclerView.getChildAt(n).getTop();
                    mRecyclerView.smoothScrollBy(0, top);
                }

            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            if (isRecyclerMove) {
                isRecyclerMove = false;
                int n = recyclerIndex - ((LinearLayoutManager) mLayoutManager).findFirstVisibleItemPosition();
                if (0 <= n && n < mRecyclerView.getChildCount()) {
                    int top = mRecyclerView.getChildAt(n).getTop();
                    mRecyclerView.scrollBy(0, top);
                }
            }
        }
    }

    protected void smoothMoveToPosition(int n) {

        int firstItem = ((LinearLayoutManager) mLayoutManager).findFirstVisibleItemPosition();
        int lastItem = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
        if (n <= firstItem) {
            mRecyclerView.smoothScrollToPosition(n);
        } else if (n <= lastItem) {
            int top = mRecyclerView.getChildAt(n - firstItem).getTop();
            mRecyclerView.smoothScrollBy(0, top);
        } else {
            mRecyclerView.smoothScrollToPosition(n);
            isRecyclerMove = true;
        }
    }
}
