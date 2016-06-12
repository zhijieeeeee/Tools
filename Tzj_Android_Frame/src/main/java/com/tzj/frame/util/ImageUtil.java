package com.tzj.frame.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * <p> ProjectName： Frame</p>
 * <p>
 * Description：图片处理工具类
 * </p>
 *
 * @author tangzhijie
 * @version 1.0
 */
public class ImageUtil {

    /**
     * 目标宽度
     */
    private static final int REQ_WIDTH = 480;

    /**
     * 目标高度
     */
    private static final int REQ_HEIGHT = 800;

    /**
     * 文件临时保存目录文件夹
     */
    public static final String TEMP_SAVE_DIR = "temp/";

    /**
     * 通过质量压缩图片
     *
     * @param inPath  要压缩的图片的路径
     * @param maxSize 要压缩到的最大尺寸，单位M
     * @return 压缩后的新文件, 新的文件名采用原文件路径的MD5
     */
    public static File compressImageByQuality(Context context, String inPath, float maxSize) {
        if (MyTextUtil.isEmpty(inPath)) {//图片路径为空
            return null;
        }
        if (maxSize <= 0) {
            maxSize = 0.1f;
        }
        File file = FunctionUtil.getCacheFile(context.getApplicationContext(), TEMP_SAVE_DIR);
        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            //源图片的bitmap
            Bitmap bitmap = getSmallBitmap(inPath, REQ_WIDTH, REQ_HEIGHT);
            //压缩后的新文件名
            String outPath = FunctionUtil.getCacheFilePath(context.getApplicationContext(), TEMP_SAVE_DIR) + MD5.getMD5(inPath);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            int options = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, os);
            //循环判断文件大小
            while (os.toByteArray().length / 1024 > maxSize * 1024 && options > 10) {
                os.reset();
                options -= 10;
                bitmap.compress(Bitmap.CompressFormat.JPEG, options, os);
            }
            FileOutputStream fos;
            fos = new FileOutputStream(outPath);
            fos.write(os.toByteArray());
            fos.flush();
            fos.close();
            //返回压缩后的文件
            return new File(outPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过图片路径获取图片Bitmap
     *
     * @param imgPath 图片路径
     * @return Bitmap
     */
    public static Bitmap getBitmap(String imgPath) {
        if (MyTextUtil.isEmpty(imgPath)) {//图片路径为空
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        return BitmapFactory.decodeFile(imgPath, options);
    }

    /**
     * 根据指定指定宽高获得压缩后的bitmap
     *
     * @param filePath  文件路径
     * @param reqWidth  指定的宽度
     * @param reqHeight 指定的高度
     * @return Bitmap
     */
    public static Bitmap getSmallBitmap(String filePath, int reqWidth, int reqHeight) {
        if (MyTextUtil.isEmpty(filePath)) {//图片路径为空
            return null;
        }
        if (reqWidth <= 0) {
            reqWidth = REQ_WIDTH;
        }
        if (reqHeight <= 0) {
            reqHeight = REQ_HEIGHT;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        //根据指定尺寸获得压缩比例
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * 根据目标宽高计算图片的缩放值
     *
     * @param options
     * @param reqWidth  目标宽度
     * @param reqHeight 目标高度
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     * 保存bitmap为文件
     *
     * @param avatarBitmap 要保存的bitmap
     * @param saveFileName 目标文件名
     * @return File
     */
    public static File saveBitmapToFile(Context context, Bitmap avatarBitmap, String saveFileName) {
        if (avatarBitmap == null) {
            return null;
        }
        try {
            File file = FunctionUtil.getCacheFile(context.getApplicationContext(), TEMP_SAVE_DIR);
            if (!file.exists()) {
                file.mkdirs();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(
                    FunctionUtil.getCacheFile(context.getApplicationContext(), TEMP_SAVE_DIR + saveFileName));
            avatarBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            return FunctionUtil.getCacheFile(context.getApplicationContext(), TEMP_SAVE_DIR + saveFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
