package cn.com.finaldemo.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Looper
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy.NONE
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import com.bumptech.glide.request.RequestOptions.circleCropTransform
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition


/**
 * Create by yinzhengwei on 2019/3/12
 * @Function
 */
fun ImageView.load(
    path: Any?,
    defaultDrawableId: Int = 0,
    errorDrawableId: Int = 0,
    corner: Int = 15,
    isCache: Boolean = true
) {
    if (closeActivity(context)) return
    //主线程加载图片，判断当前线程是否主线程 不是的话需要切换
    if (Thread.currentThread() != Looper.getMainLooper().thread) {
        (context as Activity).runOnUiThread {
            UILoad(path, corner, defaultDrawableId, isCache, errorDrawableId)
        }
    } else {
        UILoad(path, corner, defaultDrawableId, isCache, errorDrawableId)
    }
}

fun ImageView.loadOnly(
    path: Any?,
    defaultDrawableId: Int = 0,
    errorDrawableId: Int = 0,
    ivWidth: Int? = -1,
    ivHeight: Int? = -1
) {
    if (closeActivity(context)) return

    val placeholder = RequestOptions()
    if (defaultDrawableId != 0) {
        placeholder.placeholder(defaultDrawableId)
    }
    if (errorDrawableId != 0)
        placeholder.error(errorDrawableId)

    val target = Glide.with(context).load(path ?: "")
    target.apply(placeholder)

    if ((ivWidth != null && ivWidth > 0) || (ivHeight != null && ivHeight > 0)) {
        target.into(object : SimpleTarget<Drawable>() {
            override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                if (closeActivity(context)) return
                val params = this@loadOnly.layoutParams
                if (ivHeight != null && ivHeight != -1) {
                    params?.height = ivHeight
                }
                if (ivWidth != -1) {
                    params?.width = ivWidth
                }
                layoutParams = params
                setImageDrawable(resource)
            }
        })
    } else
        target.into(this@loadOnly)
}

@SuppressLint("CheckResult")
fun ImageView.loadCircle(
    path: String?,
    defaultDrawableId: Int = 0,
    errorDrawableId: Int = 0,
    isCache: Boolean = true,
    isFormHome: Boolean? = false
) {
    if (closeActivity(context)) return
    val target = Glide.with(context).load(path ?: "")

    //判断是否有默认图、缓存、加载失败图
    val placeholder = if (isFormHome == true) RequestOptions() else circleCropTransform()


    if (defaultDrawableId != 0) {
        placeholder.placeholder(defaultDrawableId)
    }
    if (!isCache) {
        placeholder.diskCacheStrategy(NONE)
    }
    if (errorDrawableId != 0)
        placeholder.error(errorDrawableId)

    target.apply(placeholder)

//    scaleType = ImageView.ScaleType.FIT_XY

    target.into(this)
}

fun ImageView.loadGif(path: Any, defaultDrawableId: Int = 0, errorDrawableId: Int = 0) {
    if (closeActivity(context)) return
    Glide.with(this)
        .asGif()
        .load(path)
        //.placeholder(defaultDrawableId)
        //.diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        //.error(errorDrawableId)
        .into(this)
}


@SuppressLint("CheckResult")
fun TextView.load(
    path: String,
    defaultDrawableId: Int? = 0,
    errorDrawableId: Int? = 0,
    isCache: Boolean = true
) {
    if (closeActivity(context)) return
    val target = Glide.with(context).load(path)

    //判断是否有默认图、缓存、加载失败图
    val placeholder = RequestOptions()
    if (defaultDrawableId != 0) {
        placeholder.placeholder(defaultDrawableId ?: 0)
    }
    if (!isCache) {
        placeholder.diskCacheStrategy(NONE)
    }
    if (errorDrawableId != 0)
        placeholder.error(errorDrawableId ?: 0)
    target.apply(placeholder)

    target.into(object : SimpleTarget<Drawable>() {
        override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
            if (closeActivity(context)) return
            background = resource
        }
    })
}

@SuppressLint("CheckResult")
fun TextView.loadRight(
    path: String?,
    defaultDrawableId: Int? = 0,
    errorDrawableId: Int? = 0,
    isCache: Boolean = true
) {
    if (closeActivity(context)) return
    val target = Glide.with(context).load(path)

    //判断是否有默认图、缓存、加载失败图
    val placeholder = RequestOptions()
    if (defaultDrawableId != 0) {
        placeholder.placeholder(defaultDrawableId ?: 0)
    }
    if (!isCache) {
        placeholder.diskCacheStrategy(NONE)
    }
    if (errorDrawableId != 0)
        placeholder.error(errorDrawableId ?: 0)
    target.apply(placeholder)

    target.into(object : SimpleTarget<Drawable>() {
        override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
            if (closeActivity(context)) return
            setCompoundDrawablesWithIntrinsicBounds(
                null, null, resource, null
            )
        }
    })
}

@SuppressLint("CheckResult")
fun TextView.loadRectangularImg(
    path: String,
    defaultDrawableId: Int? = 0,
    errorDrawableId: Int? = 0,
    isCache: Boolean = true
) {
    val target = Glide.with(context).load(path)

    //判断是否有默认图、缓存、加载失败图
    val placeholder = RequestOptions()
    if (defaultDrawableId != 0) {
        placeholder.placeholder(defaultDrawableId ?: 0)
    }
    if (!isCache) {
        placeholder.diskCacheStrategy(NONE)
    }
    if (errorDrawableId != 0)
        placeholder.error(errorDrawableId ?: 0)
    target.apply(placeholder)

    target.into(object : SimpleTarget<Drawable>() {
        override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
            /*val bitmap = resource.toBitmap()
            val params = helper.itemView.layoutParams

            val height = (bitmap.height * imageView.context.displayMetrics.widthPixels) / bitmap.width
            params.height = height
            helper.itemView.layoutParams = params
            imageView.setImage(resource)*/
            background = resource
        }
    })
}
private fun ImageView.UILoad(
    path: Any?,
    corner: Int,
    defaultDrawableId: Int,
    isCache: Boolean,
    errorDrawableId: Int
) {

    //判断是否有默认图、缓存、加载失败图
    val placeholder =
        if (corner == 0) RequestOptions() else bitmapTransform(CenterCropRoundCornerTransform(corner))

    if (defaultDrawableId != 0) {
        placeholder.placeholder(defaultDrawableId)
    }
    if (!isCache) {
        placeholder.diskCacheStrategy(NONE)
    }
    if (errorDrawableId != 0)
        placeholder.error(errorDrawableId)

    Glide.with(context).load(path ?: "").apply(placeholder).into(this)
}

@SuppressLint("CheckResult")
fun ViewGroup.load(
    path: String,
    defaultDrawableId: Int? = 0,
    errorDrawableId: Int? = 0,
    isCache: Boolean = true
) {
    if (closeActivity(context)) return
    val target = Glide.with(context).load(path)

    //判断是否有默认图、缓存、加载失败图
    val placeholder = RequestOptions()
    if (defaultDrawableId != 0) {
        placeholder.placeholder(defaultDrawableId ?: 0)
    }
    if (!isCache) {
        placeholder.diskCacheStrategy(NONE)
    }
    if (errorDrawableId != 0)
        placeholder.error(errorDrawableId ?: 0)
    target.apply(placeholder)

    target.into(object : SimpleTarget<Drawable>() {
        override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
            if (closeActivity(context)) return
            background = resource
        }
    })
}

private fun closeActivity(context: Context?) =
    null != context && context is Activity && context.isFinishing