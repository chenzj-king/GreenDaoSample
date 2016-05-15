/*
 * Copyright (c) 2015  DreamLiner Studio
 * Licensed under the Apache License, Version 2.0 (the "License”);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.chenzhongjin.greendao.sample.database.utils;

import android.content.Context;

import com.orhanobut.logger.Logger;

import cn.chenzhongjin.greendao.sample.database.DaoMaster;
import cn.chenzhongjin.greendao.sample.database.UserDao;
import de.greenrobot.dao.database.Database;

/**
 * @author chenzj
 * @Title: UpgradeHelper
 * @Description:
 * @date
 * @email admin@chenzhongjin.cn
 */
public class UpgradeEncryHelper extends DaoMaster.EncryptedOpenHelper {

    public static String TAG = UpgradeEncryHelper.class.getSimpleName();

    public UpgradeEncryHelper(Context context, String name) {
        super(context, name);
    }

    /**
     * Here is where the calls to upgrade are executed
     */
    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        //* i represent the version where the user is now and the class named with this number implies that is upgrading from i to
        for (int i = oldVersion; i < newVersion; i++) {
            Logger.t(TAG).i("Upgrading schema from version " + oldVersion + " to " + newVersion + " by migrating all tables data");
            MigrationDataBaseHelper.getInstance().migrate(db, UserDao.class);
        }
    }
}

