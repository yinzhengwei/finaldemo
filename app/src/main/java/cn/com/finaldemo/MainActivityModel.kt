package cn.com.finaldemo

import androidx.lifecycle.MutableLiveData
import cn.com.finaldemo.base.activity.IBaseView
import cn.com.finaldemo.base.viewmodel.BaseViewModel
import cn.com.finaldemo.utils.launch
import cn.com.finaldemo.utils.launchUi

/**
 * 注意：为了防止内存泄漏，这里请求完数据后回调主页时需引用BaseViewModel中的成员变量mView
 *
 */
class MainActivityModel(private var view: IBaseView) : BaseViewModel(view) {

    override fun <T> loadData(params: T?) {
        //todo 网络请求
        launch {
            launchUi {
                //todo 刷新页面
                //mView?.requestFinish()
            }
        }
    }

    override fun loadFinish(result: MutableLiveData<*>?) {
    }

    override fun loadError(msg: String) {
    }

    override fun cancelJob() {
    }
}
