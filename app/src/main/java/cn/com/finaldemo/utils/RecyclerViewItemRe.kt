package cn.com.finaldemo.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Create by yinzhengwei on 2020-08-31
 * @Function 设置列表周边的间距
 */
class CustomItemDecoration(var l: Int? = 0, var t: Int? = 0, var r: Int? = 0, var b: Int? = 0) :
    RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        outRect.set(l ?: 0, t ?: 0, r ?: 0, b ?: 0)
    }
}