package cn.com.finaldemo.widget.magicIndicator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;

/**
 * @author zhaoyong
 * @description:
 * @date :2019-10-23 14:22
 */
public class LinePagerIndicator extends LinePagerIndicatorEx {
    public int startColor = 0x9AE24840, endColor = 0x4DE24840;

    public LinePagerIndicator(Context context) {
        super(context);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        @SuppressLint("DrawAllocation") LinearGradient lg = new LinearGradient(getLineRect().left, getLineRect().top, getLineRect().right, getLineRect().bottom, new int[]{startColor, endColor}, null, LinearGradient.TileMode.CLAMP);
        getPaint().setShader(lg);
        canvas.drawRoundRect(getLineRect(), getRoundRadius(), getRoundRadius(), getPaint());
    }
}
