package com.tzj.frame.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.RadioButton;

/**
 * <p> ProjectName： Frame</p>
 * <p>
 * Description：drawableLeft跟随文字居中的RadioButton
 * </p>
 *
 * @author tangzhijie
 * @version 1.0
 * @CreateDate 2015-12-17 11:17
 */
public class DrawableCenterRadioButton extends RadioButton {

    public DrawableCenterRadioButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public DrawableCenterRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawableCenterRadioButton(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable[] drawables = getCompoundDrawables();
        if (drawables != null) {
            Drawable drawableRight = drawables[2];
            if (drawableRight != null) {
                float textWidth = getPaint().measureText(getText().toString());
                int drawablePadding = getCompoundDrawablePadding();
                int drawableWidth = drawableRight.getIntrinsicWidth();
                float bodyWidth = textWidth + drawableWidth + drawablePadding;
                setPadding(0, 0, (int) (getWidth() - bodyWidth), 0);
                canvas.translate((getWidth() - bodyWidth) / 2, 0);
            }

            Drawable drawableLeft = drawables[0];
            if (drawableLeft != null) {
                float textWidth = getPaint().measureText(getText().toString());
                int drawablePadding = getCompoundDrawablePadding();
                int drawableWidth = drawableLeft.getIntrinsicWidth();
                float bodyWidth = textWidth + drawableWidth + drawablePadding;
                setPadding((int) (getWidth() - bodyWidth), 0, 0, 0);
                canvas.translate(-(getWidth() - bodyWidth) / 2, 0);
            }
        }
        super.onDraw(canvas);
    }
}