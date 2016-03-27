package cn.chenzhongjin.greendao.sample.event;

import java.util.List;

import cn.chenzhongjin.greendao.sample.entity.User;

/**
 * @author chenzj
 * @Title: UserDataEvent
 * @Description: 类的描述 -
 * @date 2016/3/27 18:41
 * @email admin@chenzhongjin.cn
 */
public class UserDataEvent {

    private List<User> mUsers;

    public UserDataEvent(List<User> users) {
        mUsers = users;
    }

    public List<User> getUsers() {
        return mUsers;
    }

    public void setUsers(List<User> users) {
        mUsers = users;
    }

    @Override
    public String toString() {
        return "UserDataEvent{" +
                "mUsers=" + mUsers +
                '}';
    }
}

