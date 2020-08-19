package cn.com.finaldemo.base.activity

import androidx.lifecycle.MutableLiveData

/**
 *  Created by yinzhengwei on 2020-02-07.
 *  @Function
 */
interface IBaseView {

    fun showLading() {}
    fun hiddenLading() {}
    fun requestFinish(result: MutableLiveData<*>?) {}
    fun requestFinish(params: Any?, result: MutableLiveData<*>?) {}
    fun requestError(msg: String) {}

    //解绑数据
    fun unBinding()
}