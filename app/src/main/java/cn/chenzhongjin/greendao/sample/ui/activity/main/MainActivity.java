package cn.chenzhongjin.greendao.sample.ui.activity.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.dreamliner.lib.frame.base.BaseCompatActivity;

import cn.chenzhongjin.greendao.sample.R;
import cn.chenzhongjin.greendao.sample.ui.fragment.MainFragment;


/**
 * @author: chenzj
 * @Title: MainActivity
 * @Description:
 * @date: 2016/3/24 22:45
 * @email: admin@chenzhongjin.cn
 */
public class MainActivity extends BaseCompatActivity {

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        DataBindingUtil.setContentView(this, R.layout.act_main);
        loadRootFragment(R.id.frame_layout, MainFragment.newInstance());
    }
}
