# GreenDaoSample
使用新版的Greendao来进行创建Entity.运行既生成DaoMaster/DaoSession/xxDao等class.通过gradle plugin可以控制生成的db版本和生成代码的目录.

## 功能介绍 ##
### 1.通过make project的方式来生成bean&dao ###

- 引入依赖  
在项目的根目录下的build.gradle中添加greenDao gradle plugin的依赖
  
        dependencies {
        	//greendao plugin
        	classpath 'org.greenrobot:greendao-gradle-plugin:3.2.2'
    	}


- 编写Entity  

	    @Entity(active = true, nameInDb = "User")
		public class User {
		
			    @Id
			    private Long id;
			
			    private String name;
			    private String sex;
			    private String phone;
			    private Long time;
		}

- ctrl+F9 make project一下


- 执行结果如图.代表成功.会自动创建到相关的各种方法(**图略**)


### 2.增删改查功能 ###

- 新增

       	//insert to database
        User user = new User();
        user.setName(nameStr);
        user.setPhone(phoneNumStr);
        user.setSex(sexStr);
        user.setTime(System.currentTimeMillis());
        mUserDao.insert(user);

- 删除

    	//del the data
    	User user = mAdapter.getItemData(position);//我这边的逻辑是从adapter取出来.实际根据项目情况来进行getItemData
    	mUserDao.delete(user);

- 修改

    	//update message
        User mNewUser = user;
        mNewUser.setName(nameStr);
        mNewUser.setPhone(phoneNumStr);
        mNewUser.setSex(sexStr);
        mNewUser.setTime(System.currentTimeMillis());
        mUserDao.insertOrReplace(mNewUser);

- 查询

        List<WhereCondition> whereConditionList = new ArrayList<>();

        //模糊匹配姓名
        whereConditionList.add(Properties.Name.like("%" + getContent(mOperationUser.getName()) + "%"));
        //模糊匹配电话号码
        whereConditionList.add(Properties.Phone.like("%" + getContent(mOperationUser.getPhone()) + "%"));
        //精确匹配男/女
        if (!TextUtils.isEmpty(mOperationUser.getSex())) {
            whereConditionList.add(UserDao.Properties.Sex.eq(getContent(mOperationUser.getSex())));
        }
        List<User> list = mUserDao.queryBuilder().where(Properties.Time.isNotNull(), whereConditionList.toArray(new WhereCondition[]{})).list();


### 3.数据库升级工具 ###

- 获取DaoSession.会在这个过程中进行数据库升级

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

- 升级工具类

		public class UpgradeHelper extends DaoMaster.DevOpenHelper {
		
		    public static String TAG = UpgradeHelper.class.getSimpleName();
		
		    public UpgradeHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
		        super(context, name, factory);
		    }
		
		    /**
		     * Here is where the calls to upgrade are executed
		     */
		    @Override
		    public void onUpgrade(Database db, int oldVersion, int newVersion) {
		
		        /* i represent the version where the user is now and the class named with this number implies that is upgrading from i to
		        i++ schema */
		        for (int i = oldVersion; i < newVersion; i++) {
		            Log.i("greenDAO", "Upgrading schema from version " + oldVersion + " to " + newVersion + " by migrating all tables data");
		            // TODO: 2016/3/24 注意把所新版本的表的xxDao都添加到这里
		            MigrationHelper.getInstance().migrate(db, UserDao.class);
		        }
		    }
		}


## TODO ##
- 完成自定义数据类型的sample
- 编写所有查询类型的sample
- 一对多/多对多的sample

## Thanks For Open Source ##
1.  [GreenDao](https://github.com/greenrobot/greenDAO)
1.  [EventBus](https://github.com/greenrobot/EventBus)
1.  [fancybuttons](https://github.com/medyo/Fancybuttons)
1.  [logger](https://github.com/orhanobut/logger)
2.  [FlycoTabLayout](https://github.com/H07000223/FlycoTabLayout)


# 关于我 #

- **QQ:** 364972027
- **Weibo:** [http://weibo.com/u/1829515680](http://weibo.com/u/1829515680)
- **Email:** admin@chenzhongjin.cn
- **Github:** [chenzj-king](https://github.com/chenzj-king)
- **Blog:** [chenzhongjin.cn](https://www.chenzhongjin.cn)