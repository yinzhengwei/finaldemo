package cn.com.finaldemo.utils

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
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

}
