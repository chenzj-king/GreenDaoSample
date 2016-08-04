package cn.chenzhongjin.greendao.sample.ui.activity.main.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import java.util.List;

import cn.chenzhongjin.greendao.sample.R;
import cn.chenzhongjin.greendao.sample.database.User;
import cn.chenzhongjin.greendao.sample.listeners.CustomItemClickListener;

/**
 * @author: chenzj
 * @Title: UserAdapter
 * @Description:
 * @date: 2016/3/24 23:13
 * @email: admin@chenzhongjin.cn
 */
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private static final String TAG = UserAdapter.class.getSimpleName();

    private List<User> mData;
    private CustomItemClickListener mCustomItemClickListener;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_info, parent, false);
        return new ViewHolder(view, viewType, mCustomItemClickListener);
    }

    public UserAdapter(List<User> userList, CustomItemClickListener customItemClickListener) {
        mData = userList;
        mCustomItemClickListener = customItemClickListener;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User itemData = mData.get(position);

        holder.mNameTv.setText(String.format("姓名:%s", itemData.getName()));
        holder.mSexTv.setText(String.format("性别:%s", itemData.getSex()));
        holder.mPhoneNumTv.setText(String.format("电话号码:%d", itemData.getPhoneNumber()));
    }

    @Override
    public int getItemCount() {
        return null == mData ? 0 : mData.size();
    }

    public User getItemData(int position) {
        return mData == null ? null : mData.size() < position ? null : mData.get(position);
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

    public void add(User user) {
        insert(user, mData.size());
    }

    public void insert(User user, int position) {
        mData.add(position, user);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
    }

    public void clear() {
        int size = mData.size();
        mData.clear();
        notifyItemRangeRemoved(0, size);
    }

    public void addAll(List<User> userList) {
        if (null != userList && userList.size() > 0) {
            int startIndex = mData.size();
            mData.addAll(startIndex, userList);
            notifyItemRangeInserted(startIndex, userList.size());
        } else {
            Logger.t(TAG).i("userList addall is null or size = 0");
        }
    }

    public void addStartAll(List<User> userList) {
        if (null != userList && userList.size() > 0) {
            mData.addAll(0, userList);
            notifyItemRangeInserted(0, userList.size());
        } else {
            Logger.t(TAG).i("chatMsgs addall is null or size = 0");
        }
    }

}
