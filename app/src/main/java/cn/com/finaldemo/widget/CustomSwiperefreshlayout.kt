package cn.com.finaldemo.widget

import android.content.Context
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import cn.com.finaldemo.R

/**
 * Create by yinzhengwei on 2020-03-03
 * @Function
 */
class CustomSwiperefreshlayout : SwipeRefreshLayout {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet)

    init {
        setColorSchemeColors(
            ContextCompat.getColor(context, R.color.colorAccent)
        )
    }
}