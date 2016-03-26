package cn.chenzhongjin.greendao.sample.ui.activity.main;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.chenzhongjin.greendao.sample.R;
import cn.chenzhongjin.greendao.sample.ui.activity.main.adapter.CustomFragmentPagerAdapter;
import cn.chenzhongjin.greendao.sample.ui.base.BaseActivity;
import cn.chenzhongjin.greendao.sample.ui.base.BaseFragment;
import cn.chenzhongjin.greendao.sample.ui.fragment.delete.DeleteFragment;
import cn.chenzhongjin.greendao.sample.ui.fragment.insert.InsertFragment;
import cn.chenzhongjin.greendao.sample.ui.fragment.select.SelectFragment;
import cn.chenzhongjin.greendao.sample.ui.fragment.update.UpdateFragment;

/**
 * @author: chenzj
 * @Title: MainActivity
 * @Description:
 * @date: 2016/3/24 22:45
 * @email: admin@chenzhongjin.cn
 */
public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    ArrayList<BaseFragment> mBaseFragments;

    @Bind(R.id.common_toolbar)
    Toolbar mToolbar;
    @Bind(R.id.title_textview)
    TextView mTitleTv;
    @Bind(R.id.tabs)
    PagerSlidingTabStrip mPagerSlidingTabStrip;
    @Bind(R.id.viewpager)
    ViewPager mViewPager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        mToolbar.setTitle("");
        mTitleTv.setText(getString(R.string.main_title));

        mBaseFragments = new ArrayList<>();
        mBaseFragments.add(new InsertFragment());
        mBaseFragments.add(new DeleteFragment());
        mBaseFragments.add(new UpdateFragment());
        mBaseFragments.add(new SelectFragment());

        mViewPager.setAdapter(new CustomFragmentPagerAdapter(getSupportFragmentManager(), mBaseFragments));
        mViewPager.setCurrentItem(0);
        mViewPager.setOffscreenPageLimit(4);

        mPagerSlidingTabStrip.setViewPager(mViewPager);
    }

}
