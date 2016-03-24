package cn.chenzhongjin.greendao.sample.ui.base;

import cn.chenzhongjin.greendao.sample.AppContext;

/**
 * @author: chenzj
 * @Title: BaseActivity
 * @Description:
 * @date: 2016/3/24 22:51
 * @email: admin@chenzhongjin.cn
 */
public abstract class BaseActivity extends BaseCompatActivity {

    protected AppContext getAppContext() {
        return AppContext.getInstance();
    }
}
