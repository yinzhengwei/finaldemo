package cn.com.finaldemo.base

import android.app.Application
import androidx.multidex.MultiDex
import cn.com.finaldemo.utils.GmConstans.mInstance

/**
 * Create by yinzhengwei on 2020-08-12
 * @Function
 */
class CustomApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        mInstance = this

        MultiDex.install(this)
    }
}