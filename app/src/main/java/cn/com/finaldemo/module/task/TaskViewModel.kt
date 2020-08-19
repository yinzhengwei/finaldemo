package cn.com.finaldemo.module.task

import androidx.lifecycle.MutableLiveData
import cn.com.finaldemo.base.activity.IBaseView
import cn.com.finaldemo.base.viewmodel.BaseViewModel

class TaskViewModel(private var mView: IBaseView) : BaseViewModel() {
    override fun <T> loadData(params: T?) {

    }

    override fun loadFinish(result: MutableLiveData<*>?) {
    }

    override fun loadError(msg: String) {
    }

    override fun cancelJob() {
    }
}
