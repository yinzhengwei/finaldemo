package cn.com.finaldemo.module.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cn.com.finaldemo.base.activity.IBaseView
import cn.com.finaldemo.base.viewmodel.BaseViewModel

class HomeViewModel (private var mView: IBaseView) : BaseViewModel() {
    override fun <T> loadData(params: T?) {

    }

    override fun loadFinish(result: MutableLiveData<*>?) {
    }

    override fun loadError(msg: String) {
    }

    override fun cancelJob() {
    }
}
