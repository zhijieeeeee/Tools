package com.tzj.frame.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;

/**
 * <p> ProjectName： Frame</p>
 * <p>
 * Description：字母表View
 * </p>
 *
 * @author tangzhijie
 * @version 1.0
 * @CreateDate 2015/10/21
 */
public class LetterSortBar extends View {

    //要显示的字母表
    private List<String> letterList;
    private Paint paint = new Paint();
    private int select = -1;

    /**
     * 最大可设置的间隔
     */
    private int maxSpace;

    /**
     * 每个字母之间的间隔
     */
    private int space = 10;

    //开始绘制字母的起点Y坐标
    float baseY;
    //最后一个字母的Y坐标
    float lastY;

    //选择字母监听
    private OnLetterCheckedListener letterCheckedListener;

    public LetterSortBar(Context context) {
        super(context);
    }

    public LetterSortBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LetterSortBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 设置要显示的字母表
     *
     * @param letterList
     */
    public void setLetterList(List<String> letterList) {
        this.letterList = letterList;
        invalidate();
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 控件高度
        int height = getHeight();
        // 控件宽度
        int width = getWidth();
        // 每个字母的高度
        int everyLetterHeight = height / 32;

        //计算最大可设置的间隔
        maxSpace = (height - everyLetterHeight * letterList.size()) / (letterList.size() - 1);
        if (space > maxSpace) {
            space = maxSpace;
        }
        if (letterList != null && letterList.size() != 0) {
            //////////////////////////////开始绘制字母的起点Y坐标
            baseY = height / 2 - (everyLetterHeight * letterList.size() / 2) - (letterList.size() * space / 2);
            ///////////////////////////////

            for (int i = 0; i < letterList.size(); i++) {
                // 字母未被选中颜色
                paint.setColor(Color.parseColor("#999999"));
                paint.setAntiAlias(true);
                paint.setTextSize(everyLetterHeight);
                // 被选中的字母的颜色
                if (i == select) {
                    paint.setColor(Color.WHITE);
                }
                // x坐标等于控件中间-字符串宽度的一半
                float x = width / 2 - paint.measureText(letterList.get(i)) / 2;
                // y坐标
                float y = everyLetterHeight * (i + 1) + baseY + (i * space);
                if (i == letterList.size() - 1) {
                    lastY = y;
                }
                canvas.drawText(letterList.get(i), x, y, paint);
                paint.reset();
            }
        }
    }

    public boolean dispatchTouchEvent(MotionEvent event) {
        final int oldChoose = select;
        // 手指点击的Y坐标
        final float clickY = event.getY() - baseY;
        // 计算手指点击的地方为字母的第几个
        final int choosedLetter = (int) (clickY / (lastY - baseY) * letterList.size());
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:// 手指离开
//                if (letterCheckedListener != null) {
//                    letterCheckedListener.noTouchLetter();
//                }
                setBackgroundColor(Color.TRANSPARENT);
                select = -1;
                invalidate();
                break;
            default:
                setBackgroundColor(Color.parseColor("#7fAAAAAA"));
                if (oldChoose != choosedLetter) {
                    if (choosedLetter >= 0 && choosedLetter < letterList.size()) {
                        if (letterCheckedListener != null) {
                            letterCheckedListener
                                    .onLetterChecked(letterList.get(choosedLetter));
                        }
                        select = choosedLetter;
                        invalidate();
                    }
                }
                break;
        }
        return true;
    }

    /**
     * 选择字母监听
     *
     * @param letterCheckedListener
     */
    public void setOnLetterCheckedListener(
            OnLetterCheckedListener letterCheckedListener) {
        this.letterCheckedListener = letterCheckedListener;
    }

    public interface OnLetterCheckedListener {
        /**
         * 选择字母
         *
         * @param letter
         */
        public void onLetterChecked(String letter);

//        /**
//         * 手指离开字母
//         */
//        public void noTouchLetter();
    }
}
