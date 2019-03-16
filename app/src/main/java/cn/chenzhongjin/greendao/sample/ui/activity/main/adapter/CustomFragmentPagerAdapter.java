package cn.chenzhongjin.greendao.sample.ui.activity.main.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * @author chenzj
 * @Title: MyFragmentPagerAdapter
 * @Description: 类的描述 -
 * @date 2016/3/26 12:06
 * @email admin@chenzhongjin.cn
 */
public class CustomFragmentPagerAdapter extends FragmentPagerAdapter {

    ArrayList<Fragment> list;

    public CustomFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> list) {
        super(fm);
        this.list = list;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position % 4) {
            case 0:
                return "insert";
            case 1:
                return "delete";
            case 2:
                return "update";
            case 3:
                return "select";
        }
        return "";
    }
}
