package cn.chenzhongjin.greendao.sample.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dreamliner.lib.frame.base.BaseCompatFragment;
import com.flyco.tablayout.listener.OnTabSelectListener;

import cn.chenzhongjin.greendao.sample.R;
import cn.chenzhongjin.greendao.sample.databinding.FraMainBinding;
import cn.chenzhongjin.greendao.sample.ui.fragment.base.BaseFragment;

import static cn.chenzhongjin.greendao.sample.ui.fragment.DaoOperationFragment.DAO_OPERATION_DELETE_TYPE;
import static cn.chenzhongjin.greendao.sample.ui.fragment.DaoOperationFragment.DAO_OPERATION_INSERT_TYPE;
import static cn.chenzhongjin.greendao.sample.ui.fragment.DaoOperationFragment.DAO_OPERATION_SELECT_TYPE;
import static cn.chenzhongjin.greendao.sample.ui.fragment.DaoOperationFragment.DAO_OPERATION_UPDATE_TYPE;

public class MainFragment extends BaseFragment {

    private FraMainBinding mBinding;

    private BaseCompatFragment[] mFragments = new BaseCompatFragment[4];
    private int mCurrentIndex = DAO_OPERATION_INSERT_TYPE;

    private static SparseArray<String> mRightActionArray = new SparseArray<>();

    static {
        mRightActionArray.put(DAO_OPERATION_INSERT_TYPE, "新增数据");
        mRightActionArray.put(DAO_OPERATION_DELETE_TYPE, "提示");
        mRightActionArray.put(DAO_OPERATION_SELECT_TYPE, "查询数据");
    }

    public static MainFragment newInstance() {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = FraMainBinding.inflate(inflater, container, false);
        view = mBinding.getRoot();
        return view;
    }

    @Override
    protected void initViews(View view) {
        mBinding.customHead.getRightTv().setOnClickListener(v -> {
            switch (mCurrentIndex) {
                case DAO_OPERATION_INSERT_TYPE:
                    insert();
                    break;
                case DAO_OPERATION_DELETE_TYPE:
                    showToast("长按进行删除");
                    break;
                case DAO_OPERATION_SELECT_TYPE:
                    select();
                    break;
            }
        });
        mFragments[DAO_OPERATION_INSERT_TYPE] = DaoOperationFragment.newInstance(DAO_OPERATION_INSERT_TYPE);
        mFragments[DAO_OPERATION_DELETE_TYPE] = DaoOperationFragment.newInstance(DAO_OPERATION_DELETE_TYPE);
        mFragments[DAO_OPERATION_UPDATE_TYPE] = DaoOperationFragment.newInstance(DAO_OPERATION_UPDATE_TYPE);
        mFragments[DAO_OPERATION_SELECT_TYPE] = DaoOperationFragment.newInstance(DAO_OPERATION_SELECT_TYPE);

        mBinding.tabLayout.setTabData(new String[]{"新增", "删除", "更新", "查询"});
        mBinding.tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mBinding.customHead.getRightTv().setVisibility(View.INVISIBLE);
                String actionContent = mRightActionArray.get(position);
                if (!TextUtils.isEmpty(actionContent)) {
                    mBinding.customHead.setRightStr(actionContent);
                    mBinding.customHead.getRightTv().setVisibility(View.VISIBLE);
                }
                mCurrentIndex = position;
                showHideFragment(mFragments[position]);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });
        loadMultipleRootFragment(R.id.frame_layout, DAO_OPERATION_INSERT_TYPE, mFragments);
    }

    private void insert() {
        startDialog(OperationFragment.newInstance(DAO_OPERATION_INSERT_TYPE));
    }

    private void select() {
        startDialog(OperationFragment.newInstance(DAO_OPERATION_SELECT_TYPE));
    }
}
