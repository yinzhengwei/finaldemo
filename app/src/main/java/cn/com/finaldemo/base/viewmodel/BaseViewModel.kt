package cn.com.finaldemo.base.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 *  Created by yinzhengwei on 2020-02-07.
 *  @Function
 */
abstract class BaseViewModel : ViewModel() {

    //网络请求
    abstract fun <T> loadData(params: T?)

    //请求完成，回调主页刷新
    abstract fun loadFinish(result: MutableLiveData<*>?)

    //请求失败
    abstract fun loadError(msg: String)

    //取消任务
    abstract fun cancelJob()

}