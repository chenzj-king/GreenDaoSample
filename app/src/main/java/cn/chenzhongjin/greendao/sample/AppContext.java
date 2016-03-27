package cn.chenzhongjin.greendao.sample;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

import cn.chenzhongjin.greendao.sample.database.DaoMaster;
import cn.chenzhongjin.greendao.sample.database.DaoSession;
import cn.chenzhongjin.greendao.sample.database.UserDao;
import cn.chenzhongjin.greendao.sample.database.utils.UpgradeHelper;
import cn.chenzhongjin.greendao.sample.entity.User;
import cn.chenzhongjin.greendao.sample.utils.PreferencesUtils;

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
        Logger.init("dreamliner").setMethodCount(1).setMethodOffset(0).setLogLevel(LogLevel.FULL).hideThreadInfo();
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
        // 相当于得到数据库帮助对象，用于便捷获取db
        // 这里会自动执行upgrade的逻辑.backup all table→del all table→create all new table→restore data
        UpgradeHelper helper = new UpgradeHelper(this, "greendao.db", null);
        // 得到可写的数据库操作对象
        SQLiteDatabase db = helper.getWritableDatabase();
        // 获得Master实例,相当于给database包装工具
        DaoMaster daoMaster = new DaoMaster(db);
        // 获取类似于缓存管理器,提供各表的DAO类
        mDaoSession = daoMaster.newSession();
    }
}
