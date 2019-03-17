package cn.chenzhongjin.greendao.sample.utils;

import android.databinding.BindingAdapter;
import android.widget.RadioButton;

public class DbUtil {

    @BindingAdapter(value = {"checkItem"})
    public static void checkItem(RadioButton radioButton, String sex) {
        radioButton.setChecked(radioButton.getText().toString().equals(sex));
    }
}
