package cn.com.finaldemo.base.viewmodel

import android.util.Log
import androidx.lifecycle.*
import cn.com.finaldemo.base.activity.IBaseView

/**
 *  Created by yinzhengwei on 2020-02-07.
 *  @Function
 */
abstract class BaseViewModel(var mView: IBaseView?) : ViewModel(), LifecycleEventObserver {

    //网络请求
    abstract fun <T> loadData(params: T?)

    //请求完成，回调主页刷新
    abstract fun loadFinish(result: MutableLiveData<*>?)

    //请求失败
    abstract fun loadError(msg: String)

    //取消任务
    abstract fun cancelJob()

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        Log.i("setting", "event=" + event.name)
        if (event.name == "ON_DESTROY") {
            cancelJob()
            mView = null
        }
    }
}