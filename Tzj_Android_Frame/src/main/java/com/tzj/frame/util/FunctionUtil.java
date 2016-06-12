package com.tzj.frame.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.tzj.frame.common.FrameConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p> ProjectName： Frame</p>
 * <p>
 * Description：系统功能工具
 * </p>
 *
 * @author tangzhijie
 * @version 1.0
 * @CreateDate 2015-12-17 11:17
 */
public class FunctionUtil {

    /**
     * 拍照临时文件名
     */
    public static final String CAMERA_FILENAME = "temp_camera";

    /**
     * 拍照临时保存目录文件夹
     */
    public static final String CAMERA_TEMP_DIR = "cameraTemp/";

    /**
     * 关闭软键盘
     */
    public static void hideSoftKeyboard(View view, Context context) {
        if (view == null)
            return;
        InputMethodManager imm = (InputMethodManager) context.
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 打开软键盘
     */
    public static void showSoftKeyboard(View view, Context context) {
        if (view == null)
            return;
        InputMethodManager imm = (InputMethodManager) context.
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    /**
     * 调用系统相册(在onActivityResult中以Uri返回，使用data.getData()获取)
     */
    public static void useSystemPhoto(Activity activity) {
        if (activity == null) {
            return;
        }
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
        } else {
            intent = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        activity.startActivityForResult(intent, FrameConfig.REQUEST_PHOTO);
    }

    /**
     * 调用系统相机
     *
     * @param activity
     * @param fileName 临时保存的文件名,如果为空，则用CAMERA_FILENAME
     */
    public static void useSystemCamera(Activity activity, String fileName) {
        if (activity == null) {
            return;
        }
        //如果传入的文件名为空，就用默认的文件名
        if (MyTextUtil.isEmpty(fileName)) {
            fileName = CAMERA_FILENAME;
        }
        // 创建保存拍照临时图片的文件夹
        File file = getCacheFile(activity, CAMERA_TEMP_DIR);
        if (!file.exists()) {
            file.mkdirs();
        }
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri imageUri = Uri.fromFile(new File(getCacheFilePath(activity, CAMERA_TEMP_DIR) + fileName));
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        activity.startActivityForResult(openCameraIntent, FrameConfig.REQUEST_CAMERA);
    }

    /**
     * 获取选取系统照片后照片的路径
     *
     * @param context
     * @param uri     选取图片后返回的uri
     * @return
     */
    public static String getSysPhotoPath(Context context, Uri uri) {
        String[] filePathColumns = {MediaStore.Images.Media.DATA};
        Cursor c = context.getContentResolver().query(uri, filePathColumns, null, null, null);
        c.moveToFirst();
        int columnIndex = c.getColumnIndex(filePathColumns[0]);
        String picturePath = c.getString(columnIndex);
        c.close();
        return picturePath;
    }

    /**
     * 调用系统剪裁
     *
     * @param activity
     * @param imgUri   待剪裁图片uri
     */
    public static void doCrop(Activity activity, Uri imgUri) {
        if (activity == null || imgUri == null) {
            return;
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setType("image/*");
        List<ResolveInfo> list = activity.getPackageManager()
                .queryIntentActivities(intent, 0);
        if (list == null || list.size() == 0) {//未找到图片
            MyToast.show(activity, "该图片不存在");
            return;
        } else {
            intent.setData(imgUri);
            //裁剪图片宽高
            intent.putExtra("outputX", 300);
            intent.putExtra("outputY", 300);
            //宽高的比例
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("scale", true);
            intent.putExtra("return-data", true);
            Intent i = new Intent(intent);
            ResolveInfo res = list.get(0);
            i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            activity.startActivityForResult(i, FrameConfig.REQUEST_CROP);
        }
    }

    /**
     * 获得系统图库的相册图片列表（按时间先后排序）
     *
     * @param context
     * @return
     */
    public static List<String> getPhonePhotos(Context context) {
        if (context == null) {
            return null;
        }
        List<String> list = new ArrayList<>();
        // 获得系统图片
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        Uri uri = intent.getData();
        String[] proj = {MediaStore.Images.Media.DATA};
        // 通过内容提供者获得系统照片
        Cursor cursor = context.getContentResolver().query(uri, proj, null, null,
                null);
        while (cursor.moveToNext()) {
            // 获得照片的路径
            String path = cursor.getString(0);
            list.add(path);
        }
        //反序
        Collections.reverse(list);
        cursor.close();
        return list;
    }

    /**
     * 获得系统版本号
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        if (context == null) {
            return 0;
        }
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取应用程序版本名称信息
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        if (context == null) {
            return "";
        }
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获得缓存目录，有sd卡的时候是/sdcard/Android/data/<application package>cache 这个路径下面
     * 没有sd卡则是 /data/data/<application package>cache
     *
     * @param context
     * @return
     */
    public static File getCacheFile(Context context) {
        File cacheDir;
        try {
            if (CheckUtil.isSdEnable() && context.getExternalCacheDir() != null) {
                cacheDir = context.getExternalCacheDir();
            } else {
                cacheDir = context.getCacheDir();
            }
        } catch (Exception e) {
            cacheDir = context.getCacheDir();
        }
        return cacheDir;
    }

    /**
     * 获得缓存目录，有sd卡的时候是/sdcard/Android/data/<application package>cache 这个路径下面
     * 没有sd卡则是 /data/data/<application package>cache
     *
     * @param context
     * @param folderName 文件夹
     * @return
     */
    public static File getCacheFile(Context context, String folderName) {
        File cacheDir;
        try {
            if (CheckUtil.isSdEnable() && context.getExternalCacheDir() != null) {
                cacheDir = context.getExternalCacheDir();
            } else {
                cacheDir = context.getCacheDir();
            }
        } catch (Exception e) {
            cacheDir = context.getCacheDir();
        }
        if (MyTextUtil.isEmpty(folderName)) {
            return cacheDir;
        }
        return new File(cacheDir.getPath() + File.separator + folderName);
    }

    /**
     * 获得缓存目录，有sd卡的时候是/sdcard/Android/data/<application package>cache 这个路径下面
     * 没有sd卡则是 /data/data/<application package>cache
     *
     * @param context
     * @param folderName 文件夹
     * @return
     */
    public static String getCacheFilePath(Context context, String folderName) {
        File cacheDir;
        try {
            if (CheckUtil.isSdEnable() && context.getExternalCacheDir() != null) {
                cacheDir = context.getExternalCacheDir();
            } else {
                cacheDir = context.getCacheDir();
            }
        } catch (Exception e) {
            cacheDir = context.getCacheDir();
        }
        return cacheDir.getPath() + File.separator + folderName;
    }
}
