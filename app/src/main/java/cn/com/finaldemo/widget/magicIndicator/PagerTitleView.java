package cn.com.finaldemo.widget.magicIndicator;

import android.animation.FloatEvaluator;
import android.content.Context;
import android.util.TypedValue;

import net.lucode.hackware.magicindicator.buildins.ArgbEvaluatorHolder;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

/**
 * @author zhaoyong
 * @description:
 * @date :2019-10-23 15:21
 */
public class PagerTitleView extends SimplePagerTitleView {

    private float mNormalSize;

    private float mSelectSize;

    public PagerTitleView(Context context) {
        super(context);
    }

    @Override
    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
        int color = ArgbEvaluatorHolder.eval(leavePercent, mSelectedColor, mNormalColor);
        setTextColor(color);
        float size = new FloatEvaluator().evaluate(leavePercent, mSelectSize, mNormalSize);
        setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
    }

    @Override
    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
        int color = ArgbEvaluatorHolder.eval(enterPercent, mNormalColor, mSelectedColor);
        setTextColor(color);
        float size = new FloatEvaluator().evaluate(enterPercent, mNormalSize, mSelectSize);
        setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
    }

    @Override
    public void onSelected(int index, int totalCount) {
    }

    @Override
    public void onDeselected(int index, int totalCount) {
    }

    public void setNormalSize(float mNormalSize) {
        this.mNormalSize = mNormalSize;
    }

    public void setSelectSize(float mSelectSize) {
        this.mSelectSize = mSelectSize;
    }
}
