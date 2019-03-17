package cn.chenzhongjin.greendao.sample.utils;

import android.database.sqlite.SQLiteDatabase;

import com.dreamliner.lib.util.SpUtil;

import cn.chenzhongjin.greendao.sample.AppContext;
import cn.chenzhongjin.greendao.sample.database.DaoMaster;
import cn.chenzhongjin.greendao.sample.database.DaoSession;
import cn.chenzhongjin.greendao.sample.database.User;
import cn.chenzhongjin.greendao.sample.database.UserDao;
import cn.chenzhongjin.greendao.sample.database.utils.UpgradeHelper;

public enum DaoUtil {

    INSTANCE;

    private DaoSession mDaoSession;

    public void init() {
        if (SpUtil.getBoolean("isFirstStart", true)) {
            DaoSession daoSession = getDaoSession();
            UserDao userDao = daoSession.getUserDao();
            for (int i = 0; i < 10; i++) {
                User user = new User();
                user.setName("测试数据" + (i + 1));
                user.setPhone("1802730070" + String.valueOf(i));
                user.setSex(i % 3 == 0 ? "男" : "女");
                user.setTime(System.currentTimeMillis());
                userDao.insert(user);
            }
            SpUtil.putBoolean("isFirstStart", false);
        }
    }

    public synchronized DaoSession getDaoSession() {
        if (mDaoSession == null) {
            initDaoSession();
        }
        return mDaoSession;
    }

    private void initDaoSession() {
        // 相当于得到数据库帮助对象，用于便捷获取db
        // 这里会自动执行upgrade的逻辑.backup all table→del all table→create all new table→restore data
        UpgradeHelper helper = new UpgradeHelper(AppContext.getInstance(), "greendao.db", null);
        // 得到可写的数据库操作对象
        SQLiteDatabase db = helper.getWritableDatabase();
        // 获得Master实例,相当于给database包装工具
        DaoMaster daoMaster = new DaoMaster(db);
        // 获取类似于缓存管理器,提供各表的DAO类
        mDaoSession = daoMaster.newSession();
    }
}
