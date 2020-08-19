package cn.com.finaldemo.utils

import android.content.Context
import android.graphics.Typeface
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.Interpolator
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import cn.com.finaldemo.R
import cn.com.finaldemo.widget.magicIndicator.LinePagerIndicator
import cn.com.finaldemo.widget.magicIndicator.PagerTitleView
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.UIUtil
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView

/**
 * Create by yinzhengwei on 2019-11-26
 * @Function
 */
fun TabBind(
    magicIndicator: MagicIndicator, viewPager: ViewPager?, pagerIndicators: List<String>,
    normalSize: Float? = 17F,
    selectSize: Float? = 17F,
    nColor: Int? = R.color.color_88FFFFFF,
    sColor: Int? = R.color.write,
    startLineColor: Int? = R.color.write,
    endLineColor: Int? = R.color.write,
    typeFace: Typeface? = Typeface.DEFAULT,
    lineheight: Double? = 2.0,
    adjustMode: Boolean? = true
) {
    magicIndicator.navigator = CommonNavigator(magicIndicator.context).apply {
        adapter = object : CommonNavigatorAdapter() {
            override fun getTitleView(context: Context?, index: Int): IPagerTitleView {
                return PagerTitleView(context).apply {

                    typeface = typeFace ?: Typeface.DEFAULT

                    setPadding(36, 0, 36, 0)

                    text = pagerIndicators[index]
                    setNormalSize(normalSize ?: 17F)
                    setSelectSize(selectSize ?: 17F)
                    normalColor =
                        ContextCompat.getColor(context!!, nColor ?: R.color.color_88FFFFFF)
                    selectedColor = ContextCompat.getColor(context, sColor ?: R.color.write)
                    setOnClickListener {
                        viewPager?.currentItem = index
                    }
                }
            }

            override fun getCount(): Int {
                return pagerIndicators.size
            }

            override fun getIndicator(context: Context): IPagerIndicator {
                return LinePagerIndicator(context).apply {
                    startColor = ContextCompat.getColor(context, startLineColor ?: R.color.write)
                    endColor = ContextCompat.getColor(context, endLineColor ?: R.color.write)
                    mode = LinePagerIndicator.MODE_WRAP_CONTENT
                    lineHeight = UIUtil.dip2px(context, lineheight ?: 2.0).toFloat()
                    //lineWidth = UIUtil.dip2px(context, 50.0).toFloat()
                    roundRadius = UIUtil.dip2px(context, 1.5).toFloat()
                    startInterpolator = AccelerateInterpolator() as Interpolator?
                    endInterpolator = DecelerateInterpolator(2.0f)
                }
            }
        }
        isAdjustMode = adjustMode ?: true
    }
    viewPager?.run {
        ViewPagerHelper.bind(magicIndicator, this)
    }
}