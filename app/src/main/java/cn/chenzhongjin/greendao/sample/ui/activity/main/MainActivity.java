package cn.chenzhongjin.greendao.sample.ui.activity.main;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;

import java.util.ArrayList;

import butterknife.BindView;
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

    @BindView(R.id.common_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.title_textview)
    TextView mTitleTv;
    @BindView(R.id.tabs)
    PagerSlidingTabStrip mPagerSlidingTabStrip;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);

        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
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
