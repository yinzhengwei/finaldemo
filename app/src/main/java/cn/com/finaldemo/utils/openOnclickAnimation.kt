package cn.com.finaldemo.utils

import android.animation.Animator
import android.view.View
import android.view.animation.LinearInterpolator
import cn.com.finaldemo.R
import com.jakewharton.rxbinding.view.RxView
import java.util.concurrent.TimeUnit

/**
 * Create by yinzhengwei on 2020-08-21
 * @Function
 */
fun openOnclickAnimation(view: View, callback: (() -> Unit)? = null) {
    val scaleX = android.animation.ObjectAnimator.ofFloat(view, "scaleX", 1f, 0.8f, 1f)
    val scaleY = android.animation.ObjectAnimator.ofFloat(view, "scaleY", 1f, 0.8f, 1f)
    val animatorSet = android.animation.AnimatorSet()
    animatorSet.duration = 100
    animatorSet.interpolator = LinearInterpolator()
    animatorSet.playTogether(scaleX, scaleY)
    animatorSet.addListener(object : Animator.AnimatorListener {
        override fun onAnimationStart(animation: Animator) {

        }

        override fun onAnimationEnd(animation: Animator) {
            if (callback != null)
                callback()
        }

        override fun onAnimationCancel(animation: Animator) {

        }

        override fun onAnimationRepeat(animation: Animator) {

        }
    })
    animatorSet.start()
}


fun addSetOnClickListener(vararg views: View, callback: (View) -> Unit) {
    views.forEach { view ->
        RxView.clicks(view).throttleFirst(300, TimeUnit.MILLISECONDS).subscribe {
            if (!NetUtil.isNetworkAvailable(GmConstans.mInstance)) {
                ToastUtil.showToast(
                    GmConstans.mInstance?.getString(R.string.net_error_tip) ?: ""
                )
                return@subscribe
            }
            callback(view)
        }
    }
}