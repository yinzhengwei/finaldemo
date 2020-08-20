package cn.com.finaldemo.base.application

import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.os.Build
import android.webkit.WebView

/**
 * Create by yinzhengwei on 2020-08-20
 * @Function 由于Android 8.0+以上系统不支持多个进程共享一个webview数据目录，因此需要给每个进程设置一个单独的目录
 */
object ApplicationHelper {

    //给我们应用主进程之外的进程 Webview 分别设置一个目录后缀
    fun fixWebViewDataDirectoryBug(application: Application) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val processName = getProcessName(application)
            if (application.packageName != processName) {
                WebView.setDataDirectorySuffix(processName)
            }
        }
    }

    fun getProcessName(context: Context?): String? {
        if (context == null) return null
        val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (processInfo in manager.runningAppProcesses) {
            if (processInfo.pid == android.os.Process.myPid()) {
                return processInfo.processName
            }
        }
        return null
    }


}