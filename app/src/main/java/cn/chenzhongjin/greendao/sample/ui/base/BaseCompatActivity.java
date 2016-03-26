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

package cn.chenzhongjin.greendao.sample.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import org.greenrobot.eventbus.EventBus;

import java.lang.ref.WeakReference;

import cn.chenzhongjin.greendao.sample.utils.MdDialogFactory;


/**
 * @author chenzj
 * @Title: BaseActivity
 * @Description: 类的描述 - 用以初始化Eventbus.Dialog等等相关
 * @date 2015-5-18
 * @email chenzhongjin@vip.qq.com
 */
public abstract class BaseCompatActivity extends AppCompatActivity {

    /**
     * Screen information
     */
    protected int mScreenWidth = 0;
    protected int mScreenHeight = 0;
    protected float mScreenDensity = 0.0f;

    /**
     * 弱引用Act来执行Handler
     */
    public MyHandler mHandler;

    protected MaterialDialog materialDialog;

    /**
     * judge reisterEvent or not
     *
     * @return
     */
    protected boolean isRegisterEvent() {
        return false;
    }

    /**
     * get bundle data
     *
     * @param extras
     */
    protected void getBundleExtras(Bundle extras) {
    }

    /**
     * 获得主界面的layout的id
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 初始化特别的界面.如果定制的Head等等就会用到.
     */
    protected void initSpecialView() {
    }

    /**
     * 初始化界面
     */
    protected abstract void initViews();

    /**
     * get Handler mes to deal the job
     *
     * @param msg
     */
    public void handleMes(Message msg) {
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isRegisterEvent()) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        // base setup
        Bundle extras = getIntent().getExtras();
        if (null != extras) {
            getBundleExtras(extras);
        }

        mHandler = new MyHandler(this);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        mScreenDensity = displayMetrics.density;
        mScreenHeight = displayMetrics.heightPixels;
        mScreenWidth = displayMetrics.widthPixels;

        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
        } else {
            throw new IllegalArgumentException("You must return a right contentView layout resource Id");
        }

        initSpecialView();
        initViews();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isRegisterEvent()) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void finish() {
        super.finish();
    }

    /**
     * startActivity
     *
     * @param clazz
     */
    public void readyGo(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    /**
     * startActivity with bundle
     *
     * @param clazz
     * @param bundle
     */
    public void readyGo(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * startActivity then finish
     *
     * @param clazz
     */
    public void readyGoThenKill(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
        finish();
    }

    /**
     * startActivity with bundle then finish
     *
     * @param clazz
     * @param bundle
     */
    public void readyGoThenKill(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        finish();
    }

    /**
     * startActivityForResult
     *
     * @param clazz
     * @param requestCode
     */
    public void readyGoForResult(Class<?> clazz, int requestCode) {
        Intent intent = new Intent(this, clazz);
        startActivityForResult(intent, requestCode);
    }

    /**
     * startActivityForResult with bundle
     *
     * @param clazz
     * @param requestCode
     * @param bundle
     */
    public void readyGoForResult(Class<?> clazz, int requestCode, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    protected Toast mToast;

    public Toast getToast() {
        return mToast;
    }

    /**
     * show toast
     *
     * @param msg
     */
    public void showToast(final String msg) {
        // 防止遮盖虚拟按键
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (!TextUtils.isEmpty(msg)) {
                    mToast = Toast.makeText(BaseCompatActivity.this, TextUtils.concat("", msg), Toast.LENGTH_SHORT);
                    mToast.show();
                }
            }
        });
    }

    public void showToast(final int resId) {

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mToast = Toast.makeText(BaseCompatActivity.this, resId, Toast.LENGTH_SHORT);
                mToast.show();
            }
        });
    }

    protected static class MyHandler extends Handler {

        private final WeakReference<BaseCompatActivity> mActivity;

        public MyHandler(BaseCompatActivity activity) {
            mActivity = new WeakReference<BaseCompatActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            BaseCompatActivity activity = mActivity.get();
            if (activity != null) {
                //do someThing
                activity.handleMes(msg);
            }
        }
    }

    public boolean isShowIngDialog() {
        return null != materialDialog && (materialDialog.isShowing());
    }

    public void hideDialog() {
        if (null != materialDialog && materialDialog.isShowing()) {
            materialDialog.dismiss();
        }
    }

    public void showBaseDialog(String title, String content, String positiveText, String negativeText, MaterialDialog
            .SingleButtonCallback singleButtonCallback) {
        materialDialog = MdDialogFactory.showCallbacks(this, title, content, positiveText, negativeText, singleButtonCallback);
        materialDialog.show();
    }
}
