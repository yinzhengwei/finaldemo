package cn.com.finaldemo.utils

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import cn.com.finaldemo.R

/**
 * @Function:Toast工具类
 */
object ToastUtil {

    private var toast: Toast? = null
    fun showToast(message: String = "") {
        if (TextUtils.isEmpty(message)) {
            return
        }
        if (Looper.getMainLooper().thread != Thread.currentThread()) {
            object : Handler(Looper.getMainLooper()) {
                override fun handleMessage(msg: Message) {
                    showToast(message)
                }
            }.sendEmptyMessage(0)
            return
        }
        try {
            toast?.cancel()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        toast = Toast.makeText(GmConstans.mInstance, "", Toast.LENGTH_SHORT)
        val view = LayoutInflater.from(GmConstans.mInstance).inflate(R.layout.toast, null)
        view.findViewById<TextView>(R.id.tv_toast_message).text = message
        toast?.view = view
        toast?.setGravity(Gravity.CENTER, 0, 0)
        toast?.show()
    }

    fun showToast_Text(text: String) {
        showText(text, 20)
    }

    fun showToast_Text(text: String, footSize: Int) {
        showText(text, footSize)
    }

    fun cancelToast() {
        if (toast != null) {
            toast?.cancel()
            toast = null
        }
    }

    @SuppressLint("WrongConstant")
    private fun showText(text: String, footSize: Int) {
        cancelToast()
        toast = Toast(GmConstans.mInstance)
        toast?.setGravity(Gravity.CENTER, 0, 0)
        val view = LayoutInflater.from(GmConstans.mInstance)
            .inflate(R.layout.layout_sign_toast, null)
        val layout_sign = view.findViewById<View>(R.id.layout_sign) as RelativeLayout
        val textView = view.findViewById<View>(R.id.tv_sign) as TextView
        textView.textSize = footSize.toFloat()
        textView.text = text
        toast?.view = view
        toast?.duration = 3000
        toast?.show()

        val alpha = ObjectAnimator.ofFloat(layout_sign, "alpha", 0f, 1f)
        alpha.duration = 300
        alpha.interpolator = AccelerateInterpolator()
        alpha.start()

        val scaleX = ObjectAnimator.ofFloat(
            layout_sign,
            "scaleX",
            1f,
            1.2f,
            1.5f,
            1.2f,
            1f,
            1.2f,
            1.5f,
            1.2f,
            1f
        )
        val scaleY = ObjectAnimator.ofFloat(
            layout_sign,
            "scaleY",
            1f,
            1.2f,
            1.5f,
            1.2f,
            1f,
            1.2f,
            1.5f,
            1.2f,
            1f
        )
        val set = AnimatorSet()
        set.duration = 700
        alpha.interpolator = LinearInterpolator()
        set.playTogether(scaleX, scaleY)
        set.startDelay = 300
        set.start()
    }
}
