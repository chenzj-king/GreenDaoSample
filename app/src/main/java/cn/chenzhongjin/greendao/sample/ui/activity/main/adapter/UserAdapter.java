package cn.chenzhongjin.greendao.sample.ui.activity.main.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.chenzhongjin.greendao.sample.R;
import cn.chenzhongjin.greendao.sample.entity.User;
import cn.chenzhongjin.greendao.sample.listeners.CustomItemClickListener;

/**
 * @author: chenzj
 * @Title: UserAdapter
 * @Description:
 * @date: 2016/3/24 23:13
 * @email: admin@chenzhongjin.cn
 */
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private List<User> mUserList;
    private CustomItemClickListener mCustomItemClickListener;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_info, parent, false);
        return new ViewHolder(view, viewType, mCustomItemClickListener);
    }

    public UserAdapter(List<User> userList, CustomItemClickListener customItemClickListener) {
        mUserList = userList;
        mCustomItemClickListener = customItemClickListener;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return null == mUserList ? 0 : mUserList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CustomItemClickListener mCustomItemClickListener;
        TextView mNameTv;
        TextView mSexTv;
        TextView mPhoneNumTv;

        public ViewHolder(View itemView, int viewType, CustomItemClickListener customItemClickListener) {
            super(itemView);

            mCustomItemClickListener = customItemClickListener;

            mNameTv = (TextView) itemView.findViewById(R.id.user_info_name);
            mSexTv = (TextView) itemView.findViewById(R.id.user_info_sex);
            mPhoneNumTv = (TextView) itemView.findViewById(R.id.user_info_phone_number);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (null != mCustomItemClickListener) {
                mCustomItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }
    }
}
