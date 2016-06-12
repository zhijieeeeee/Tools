package com.tzj.frame.util;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p> ProjectName： Frame</p>
 * <p>
 * Description：文本常用工具类
 * </p>
 *
 * @author tangzhijie
 * @version 1.0
 * @CreateDate 2015-12-17 11:17
 */
public class MyTextUtil {

    /**
     * 字符串判空
     *
     * @param text
     * @return
     */
    public static boolean isEmpty(String text) {
        if (null == text || text.equals("") || (text.trim()).equals(""))
            return true;
        return false;
    }

    /**
     * double转化为int
     *
     * @param i
     * @return
     */
    public static int double2Int(double i) {
        return Integer.parseInt(new java.text.DecimalFormat("0").format(i));
    }

    /**
     * double转化为保留2位小数的String
     *
     * @param i
     * @return
     */
    public static String double2TwoDecimalString(double i) {
        return String.format("%.2f", i);
    }

    /**
     * double保留2位小数
     *
     * @param i
     * @return
     */
    public static String double2TwoDecimalDouble(double i) {
        DecimalFormat df=new DecimalFormat("######0.00");
        return df.format(i);
    }

    /**
     * 手机号码格式是否正确
     *
     * @param mobiles 手机号码
     * @return
     */
    public static boolean isMobile(String mobiles) {
        if (isEmpty(mobiles))
            return false;
        Pattern p = Pattern.compile("^((13[0-9])|(17[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 是否是n-m位字母或数字(包含特殊字符，排除任何不可见字符，包括空格、制表符、换页符等等)
     *
     * @param pwd 密码
     * @param n   最低位数
     * @param m   最高位数
     * @return
     */
    public static boolean isN2MPwd(String pwd, int n, int m) {
        if (isEmpty(pwd))
            return false;
        if (n >= 0 && m > n) {
            Pattern p = Pattern.compile("^[^\\s]{" + n + "," + m + "}$");
            Matcher matcher = p.matcher(pwd);
            return matcher.matches();
        }
        return false;
    }

    /**
     * 是否是身份证
     *
     * @param str
     * @return
     */
    public static boolean isIdentity(String str) {
        if (isEmpty(str))
            return false;
        if (str.length() == 15)
            return Pattern.compile("^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$").matcher(str).matches();
        if (str.length() == 18)
            return Pattern.compile("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$").matcher(str).matches();
        return false;
    }

    /**
     * 邮箱验证
     *
     * @param str
     * @return
     */
    public static boolean isEmail(String str) {
        if (isEmpty(str))
            return false;
        return Pattern
                .compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$")
                .matcher(str).matches();
    }

}
