package cn.chenzhongjin.greendao.sample;

import android.app.Application;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

import cn.chenzhongjin.greendao.sample.database.DaoMaster;
import cn.chenzhongjin.greendao.sample.database.DaoSession;
import cn.chenzhongjin.greendao.sample.database.UserDao;
import cn.chenzhongjin.greendao.sample.database.utils.UpgradeEncryHelper;
import cn.chenzhongjin.greendao.sample.entity.User;
import cn.chenzhongjin.greendao.sample.utils.PreferencesUtils;
import de.greenrobot.dao.database.Database;

/**
 * @author: chenzj
 * @Title: AppContext
 * @Description:
 * @date: 2016/3/24 22:42
 * @email: admin@chenzhongjin.cn
 */
public class AppContext extends Application {

    private static AppContext instance;
    private DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        initLogger();

        if (PreferencesUtils.getBoolean(this, PreferencesUtils.FIRST_START, true)) {
            Logger.i("first Start");
            DaoSession daoSession = getDaoSession();
            UserDao userDao = daoSession.getUserDao();

            for (int i = 0; i < 10; i++) {
                User user = new User();
                user.setName("测试数据" + (i + 1));
                user.setPhoneNumber((Long.parseLong("1802730070") * 10 + i));
                user.setSex(i % 3 == 0 ? "男" : "女");
                user.setUpdateTime(System.currentTimeMillis());

                userDao.insert(user);
            }
            PreferencesUtils.putBoolean(this, PreferencesUtils.FIRST_START, false);
        }
    }

    private void initLogger() {
        Logger.init("dreamliner").methodCount(1).methodOffset(0).logLevel(LogLevel.FULL).hideThreadInfo();
    }

    public static AppContext getInstance() {
        return instance;
    }

    /*
     * GreenDao相关
     */
    public synchronized DaoSession getDaoSession() {
        if (mDaoSession == null) {
            initDaoSession();
        }
        return mDaoSession;
    }

    private void initDaoSession() {
        Database db = new UpgradeEncryHelper(this, "greendao.db").getWritableDatabase("dreamliner");
        mDaoSession = new DaoMaster(db).newSession();
    }
}
