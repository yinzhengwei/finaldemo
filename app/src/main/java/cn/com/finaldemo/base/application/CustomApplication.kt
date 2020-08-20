package cn.com.finaldemo.base.application

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import cn.com.finaldemo.utils.GmConstans.mInstance

/**
 * Create by yinzhengwei on 2020-08-12
 * @Function
 */
class CustomApplication : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)

        MultiDex.install(this)

        //防止多进程状态下，在 Android 9 及以上版本，不允许在不同的进程使用相同的 Webview 目录。
        ApplicationHelper.fixWebViewDataDirectoryBug(this)
    }

    override fun onCreate() {
        super.onCreate()

        mInstance = this


    }
}