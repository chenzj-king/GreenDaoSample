package cn.chenzhongjin.greendao.sample.widget.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @author chenzj
 * @Title: MyFragmentPagerAdapter
 * @Description: 类的描述 -
 * @date 2016/3/26 12:06
 * @email admin@chenzhongjin.cn
 */
public class CustomFragmentPagerAdapter extends FragmentPagerAdapter {

    List<Fragment> mFragments;

    public CustomFragmentPagerAdapter(FragmentManager fm, List<Fragment> mFragments) {
        super(fm);
        this.mFragments = mFragments;
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }
}
