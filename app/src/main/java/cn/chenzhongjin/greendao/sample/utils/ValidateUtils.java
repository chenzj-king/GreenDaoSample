package cn.chenzhongjin.greendao.sample.utils;

import java.util.regex.Pattern;

/**
 * @author chenzj
 * @Title: ValidateUtils
 * @Description: 类的描述 -
 * @date 2016/3/26 11:12
 * @email admin@chenzhongjin.cn
 */
public class ValidateUtils {

    private static Pattern phonePattern;
    private static Pattern cmccPattern;
    private static Pattern cuPattern;
    private static Pattern ctPattern;

    private static void initPhonePatter() {
        phonePattern = Pattern.compile("^\\d{11}$");
        cmccPattern = Pattern.compile("^1((34|35|36|37|38|39|47|50|51|52|57|58|59|78|82|83|84|87|88)[0-9]|705)\\d{3,7}$");
        cuPattern = Pattern.compile("^1((30|31|32|45|55|56|76|85|86)[0-9]|709)\\d{3,7}$");
        ctPattern = Pattern.compile("^1((33|53|80|81|89|77)[0-9]|700)\\d{3,7}$");
    }

    public static boolean isPhoneNumber(String phoneNumber) {
        if (null == phonePattern || null == cmccPattern || null == cuPattern || null == ctPattern)
            initPhonePatter();
        return (phonePattern.matcher(phoneNumber).matches()) && (cmccPattern.matcher(phoneNumber).matches()
                || cuPattern.matcher(phoneNumber).matches() || ctPattern.matcher(phoneNumber).matches());
    }
}
