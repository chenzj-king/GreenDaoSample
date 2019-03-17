package cn.chenzhongjin.greendao.sample.entity;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import cn.chenzhongjin.greendao.sample.BR;

public class OperationUser extends BaseObservable {

    private String name;
    private String sex;
    private String phone;

    public OperationUser() {
    }

    public OperationUser(String name, String sex, String phone) {
        this.name = name;
        this.sex = sex;
        this.phone = phone;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
        notifyPropertyChanged(BR.sex);
    }

    @Bindable
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        notifyPropertyChanged(BR.phone);
    }

    @Override
    public String toString() {
        return "OperationUser{" +
                "name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
