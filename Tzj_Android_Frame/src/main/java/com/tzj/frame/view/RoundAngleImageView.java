package com.tzj.frame.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.tzj.frame.R;

/**
 * <p> ProjectName： Frame</p>
 * <p>
 * Description：圆角ImageView
 * </p>
 *
 * @author tangzhijie
 * @version 1.0
 * @CreateDate 2015-12-17 11:17
 */
public class RoundAngleImageView extends ImageView {

    private Paint paint;// 绘制圆角的笔
    private int round = 10;// 圆角半径
    private Paint paint2;// 绘制图片的笔

    public RoundAngleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initPaint();
    }

    public RoundAngleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.RoundAngleImageView);
        round = a.getDimensionPixelSize(R.styleable.RoundAngleImageView_corner_radius,
                round);
        initPaint();
    }

    public RoundAngleImageView(Context context) {
        super(context);
        initPaint();
    }

    private void initPaint() {
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));

        paint2 = new Paint();
        paint2.setAntiAlias(true);
        paint2.setXfermode(null);
    }

    public void draw(Canvas canvas) {
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(),
                Config.ARGB_4444);
        Canvas canvas2 = new Canvas(bitmap);
        super.draw(canvas2);
        drawLiftUp(canvas2);// 绘制左上圆角
        drawRightUp(canvas2);// 绘制又上圆角
        drawLiftDown(canvas2);// 绘制左下圆角
        drawRightDown(canvas2);// 绘制右下圆角
        // 在绘制后的画布上画图片
        canvas.drawBitmap(bitmap, 0, 0, paint2);
    }

    private void drawLiftUp(Canvas canvas) {
        Path path = new Path();
        path.moveTo(0, round);
        path.lineTo(0, 0);
        path.lineTo(round, 0);
        // 画一个弧线的路径
        path.arcTo(new RectF(0, 0, round * 2, round * 2), -90, -90);
        path.close();
        canvas.drawPath(path, paint);
    }

    private void drawLiftDown(Canvas canvas) {
        Path path = new Path();
        path.moveTo(0, getHeight() - round);
        path.lineTo(0, getHeight());
        path.lineTo(round, getHeight());
        path.arcTo(new RectF(0, getHeight() - round * 2, 0 + round * 2,
                getHeight()), 90, 90);
        path.close();
        canvas.drawPath(path, paint);
    }

    private void drawRightDown(Canvas canvas) {
        Path path = new Path();
        path.moveTo(getWidth() - round, getHeight());
        path.lineTo(getWidth(), getHeight());
        path.lineTo(getWidth(), getHeight() - round);
        path.arcTo(new RectF(getWidth() - round * 2, getHeight() - round * 2,
                getWidth(), getHeight()), 0, 90);
        path.close();
        canvas.drawPath(path, paint);
    }

    private void drawRightUp(Canvas canvas) {
        Path path = new Path();
        path.moveTo(getWidth(), round);
        path.lineTo(getWidth(), 0);
        path.lineTo(getWidth() - round, 0);
        path.arcTo(new RectF(getWidth() - round * 2, 0, getWidth(),
                0 + round * 2), -90, 90);
        path.close();
        canvas.drawPath(path, paint);
    }

}
