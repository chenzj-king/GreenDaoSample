package cn.chenzhongjin.greendao.sample.eventbus;

import java.util.List;

import cn.chenzhongjin.greendao.sample.database.User;

public class SelectEvent {

    private List<User> mUsers;

    public SelectEvent(List<User> users) {
        mUsers = users;
    }

    public List<User> getUsers() {
        return mUsers;
    }
}
