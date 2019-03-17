package cn.chenzhongjin.greendao.sample;

import android.app.Application;

import com.dreamliner.lib.util.UtilConfig;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import cn.chenzhongjin.greendao.sample.utils.DaoUtil;

/**
 * @author: chenzj
 * @Title: AppContext
 * @Description:
 * @date: 2016/3/24 22:42
 * @email: admin@chenzhongjin.cn
 */
public class AppContext extends Application {

    private static AppContext mInstance;

    public static AppContext getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        UtilConfig.init(this, BuildConfig.DEBUG, new String[]{});
        initLogger();
        DaoUtil.INSTANCE.init();
    }

    private void initLogger() {
        PrettyFormatStrategy strategy = PrettyFormatStrategy.newBuilder().showThreadInfo(false).methodCount(2).tag("dreamliner").build();
        Logger.addLogAdapter(new AndroidLogAdapter(strategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });
    }


}
