package com.tt.tools.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;


/**
 * <p> ProjectName： TzjCustomView</p>
 * <p>
 * Description：旋转并缩放的三角形与圆形的结合View（高仿百度加载图片的加载动画）
 * </p>
 *
 * @author tangzhijie
 * @version 1.0
 * @createdate 2016-01-28 13:58
 */
public class TriangleWithCircleView extends View {

    /**
     * 三角形画笔
     */
    private Paint trianglePaint;

    /**
     * 圆的画笔
     */
    private Paint circlePaint;

    /**
     * 最大三角形的长
     */
    private static final int MAX_BORDER = 40;

    /**
     * 最小三角形的长
     */
    private static final int MIN_BORDER = 15;

    /**
     * 当前最小三角形的长
     */
    private int currentMinBorder;

    /**
     * 当前最大三角形的长
     */
    private int currentMaxBorder;

    /**
     * 缩放动画的速率
     */
    private static final int STEP = 1;

    /**
     * 当前的三角形的长
     */
    private int currentBorderLength;

    /**
     * 是否是缩小
     */
    private boolean isDown = true;

    /**
     * 三个圆的半径
     */
    private static final int CIRCLE_1 = 8;
    private static final int CIRCLE_2 = 12;
    private static final int CIRCLE_3 = 16;

    public TriangleWithCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(100, 100);
    }

    /**
     * 初始化
     */
    private void init() {
        trianglePaint = new Paint();
        trianglePaint.setColor(Color.parseColor("#096EFF"));
        trianglePaint.setStrokeWidth(2);
        trianglePaint.setStyle(Paint.Style.STROKE);
        trianglePaint.setAntiAlias(true);

        circlePaint = new Paint();
        circlePaint.setColor(Color.parseColor("#096EFF"));
        circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setAntiAlias(true);

        startRotation();
        currentBorderLength = MAX_BORDER;
        currentMinBorder = MIN_BORDER;
        currentMaxBorder = MAX_BORDER;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制三角形
        Path path = new Path();
        float gen3 = (float) Math.sqrt(3);
        //三角形三点与边距的距离
        float offset = getHeight() / 2 - currentBorderLength;

        //三角形上点的坐标
        float topX = getWidth() / 2;
        float topY = offset;

        //三角形左点的坐标
        float leftX = getWidth() / 2 - (gen3 / 2 * currentBorderLength);
        float leftY = getHeight() / 2 + currentBorderLength / 2;

        //三角形右点的坐标
        float rightX = getWidth() / 2 + (gen3 / 2 * currentBorderLength);
        float rightY = getHeight() / 2 + currentBorderLength / 2 - 10;

        path.moveTo(topX, topY);//上
        path.lineTo(leftX, leftY);//左
        path.lineTo(rightX, rightY);//右
        path.lineTo(topX, topY);
        path.close();
        canvas.drawPath(path, trianglePaint);

        //根据三角形的三个角绘制三个圆
        canvas.drawCircle(topX, topY, CIRCLE_1, circlePaint);
        canvas.drawCircle(leftX, leftY, CIRCLE_2, circlePaint);
        canvas.drawCircle(rightX, rightY, CIRCLE_3, circlePaint);

        if (isDown) {//当前状态为缩小
            currentBorderLength -= STEP;
        } else {//当前状态为放大
            currentBorderLength += STEP;
        }

        //判断是否缩小到最小的
        if (currentBorderLength <= currentMinBorder) {
            //减少下次能放大的最大值
            currentMaxBorder -= 10;
            if (currentMaxBorder <= MAX_BORDER - 20) {
                currentMaxBorder = MAX_BORDER;
            }
            isDown = false;
        }
        if (currentBorderLength >= currentMaxBorder) {
            //增大下次能缩小的最小值
            currentMinBorder += 15;
            if (currentMinBorder >= MIN_BORDER - 10) {
                currentMinBorder = MIN_BORDER;
            }
            isDown = true;
        }
        invalidate();
    }

    /**
     * 开始旋转
     */
    private void startRotation() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "rotation", 0, 360);
        objectAnimator.setDuration(3000);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.start();
    }
}
