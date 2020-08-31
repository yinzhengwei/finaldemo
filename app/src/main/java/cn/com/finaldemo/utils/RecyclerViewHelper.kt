package cn.com.finaldemo.utils

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter

/**
 * Create by yinzhengwei on 2020-08-21
 * @Function
 */
fun RecyclerView.initLinearLayout(
    adapter: BaseQuickAdapter<*, *>,
    mOrientation: Int,
    l: Int? = 0, t: Int? = 0, r: Int? = 0, b: Int? = 0,
    callback: (Int) -> Unit
) {
    setHasFixedSize(true)
    setAdapter(adapter)

    //添加周边间距
    while (itemDecorationCount > 0)
        removeItemDecorationAt(0)
    addItemDecoration(CustomItemDecoration(l, t, r, b))

    layoutManager = LinearLayoutManager(context).apply {
        orientation = mOrientation
    }
    adapter.setOnItemClickListener { _, _, position ->
        callback(position)
    }
}

fun RecyclerView.initGridLayout(
    adapter: BaseQuickAdapter<*, *>,
    cluNum: Int? = 0,
    l: Int? = 0, t: Int? = 0, r: Int? = 0, b: Int? = 0,
    callback: (Int) -> Unit
) {
    setHasFixedSize(true)
    setAdapter(adapter)

    //添加周边间距
    while (itemDecorationCount > 0)
        removeItemDecorationAt(0)
    addItemDecoration(CustomItemDecoration(l, t, r, b))

    layoutManager = GridLayoutManager(context, cluNum ?: 1)

    adapter.setOnItemClickListener { _, _, position ->
        callback(position)
    }
}