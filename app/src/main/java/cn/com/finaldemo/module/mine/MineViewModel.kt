package cn.com.finaldemo.module.mine

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cn.com.finaldemo.base.activity.IBaseView
import cn.com.finaldemo.base.viewmodel.BaseViewModel

/**
 * 注意：为了防止内存泄漏，这里请求完数据后回调主页时需引用BaseViewModel中的成员变量mView
 *
 */
class MineViewModel (private var view: IBaseView) : BaseViewModel(view) {
    override fun <T> loadData(params: T?) {

    }

    override fun loadFinish(result: MutableLiveData<*>?) {
    }

    override fun loadError(msg: String) {
    }

    override fun cancelJob() {
    }
}
