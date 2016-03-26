package cn.chenzhongjin.greendao.sample.ui.base;

import cn.chenzhongjin.greendao.sample.AppContext;

/**
 * @author chenzj
 * @Title: BaseFragment
 * @Description: 类的描述 -
 * @date 2016/3/26 11:08
 * @email admin@chenzhongjin.cn
 */
public abstract class BaseFragment extends BaseLazyFragment {

    protected AppContext getAppContext() {
        return AppContext.getInstance();
    }

}

