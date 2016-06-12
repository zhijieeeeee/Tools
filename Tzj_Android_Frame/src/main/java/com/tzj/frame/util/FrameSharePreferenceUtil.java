package com.tzj.frame.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * <p> FileName： FrameSharePreferenceUtil</p>
 * <p>
 * Description：SharePreference基类
 * </p>
 *
 * @author tangzhijie
 * @version 1.0
 * @createdate 2016-01-07 16:54
 */
public class FrameSharePreferenceUtil {

    /**
     * 保存对象到SharePreference
     *
     * @param context
     * @param spName    SharePreference名称
     * @param paramName 键
     * @param <T>       对象
     */
    public static <T extends Object> void saveObjectToSp(Context context, String spName, String paramName, T object) {
        SharedPreferences sharePreference = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharePreference.edit();
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);
            //将类做Base64加密为字符串
            String base64String = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
            editor.putString(paramName, base64String);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获得保存在SharePreference中的对象
     *
     * @param context
     * @param spName    SharePreference名称
     * @param paramName 键
     * @param <T>       对象
     * @return
     */
    public static <T extends Object> T getObjectSpParams(Context context, String spName, String paramName) {
        SharedPreferences sharePreference = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        String base64String = sharePreference.getString(paramName, "");
        if (MyTextUtil.isEmpty(base64String)) {
            return null;
        } else {
            try {
                byte[] buffer = Base64.decode(base64String.getBytes(), Base64.DEFAULT);
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buffer);
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                return (T) objectInputStream.readObject();
            } catch (Exception e) {
                return null;
            }
        }
    }
}
