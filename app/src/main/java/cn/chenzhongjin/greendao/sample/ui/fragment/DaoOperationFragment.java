package cn.chenzhongjin.greendao.sample.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dreamliner.rvhelper.adapter.BaseDBAdapter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import cn.chenzhongjin.greendao.sample.R;
import cn.chenzhongjin.greendao.sample.database.User;
import cn.chenzhongjin.greendao.sample.databinding.FraDaoOperationBinding;
import cn.chenzhongjin.greendao.sample.eventbus.SelectEvent;
import cn.chenzhongjin.greendao.sample.eventbus.UserChangeEvent;
import cn.chenzhongjin.greendao.sample.ui.fragment.base.BaseDaoOperation;
import cn.chenzhongjin.greendao.sample.ui.fragment.base.BaseFragment;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * @author chenzj
 * @Title: DaoOperationFragment
 * @Description: 类的描述 -
 * @date 2016/3/26 11:12
 * @email admin@chenzhongjin.cn
 */
public class DaoOperationFragment extends BaseFragment {

    public static final int DAO_OPERATION_INSERT_TYPE = 0;
    public static final int DAO_OPERATION_DELETE_TYPE = 1;
    public static final int DAO_OPERATION_UPDATE_TYPE = 2;
    public static final int DAO_OPERATION_SELECT_TYPE = 3;

    public static final String BD_DAO_OPERATION_TYPE_KEY = "daoOperationType";

    protected FraDaoOperationBinding mBinding;

    public BaseDBAdapter<User> mAdapter;

    protected BaseDaoOperation mBaseDaoOperation;

    public static DaoOperationFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt(BD_DAO_OPERATION_TYPE_KEY, type);
        DaoOperationFragment fragment = new DaoOperationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = FraDaoOperationBinding.inflate(inflater, container, false);
        view = mBinding.getRoot();
        return view;
    }

    @Override
    protected void initViews(View view) {
        mBaseDaoOperation = BaseDaoOperation.getDaoOperationByType(mContext, this, mType);
        mAdapter = new BaseDBAdapter<>(mBaseDaoOperation::onItemClick, mBaseDaoOperation::onItemLongClick, R.layout.item_db_user_info);

        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mBinding.recyclerView.setAdapter(mAdapter);

        mBinding.recyclerView.setRefreshListener(ptrFrameLayout -> loadAllDataByLocal());
        loadAllDataByLocal();
    }

    private void loadAllDataByLocal() {
        Observable.just("")
                .subscribeOn(Schedulers.io())
                .flatMap(s -> Observable.just(mUserDao.loadAll())
                )
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindToLifecycle())
                .subscribe(new DisposableObserver<List<User>>() {
                    @Override
                    public void onNext(List<User> users) {
                        mAdapter.update(users);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userChange(UserChangeEvent userChangeEvent) {
        loadAllDataByLocal();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void selectEvent(SelectEvent selectEvent) {
        if (mType == DAO_OPERATION_SELECT_TYPE) {
            mAdapter.update(selectEvent.getUsers());
        }
    }
}

