package cn.com.finaldemo

import androidx.lifecycle.MutableLiveData
import cn.com.finaldemo.base.activity.IBaseView
import cn.com.finaldemo.base.viewmodel.BaseViewModel
import cn.com.finaldemo.utils.launch
import cn.com.finaldemo.utils.launchUi

class MainActivityModel(private var mView: IBaseView) : BaseViewModel() {

    override fun <T> loadData(params: T?) {
        //todo 网络请求
        launch {
            launchUi {
                //todo 刷新页面
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
