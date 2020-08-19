package cn.com.finaldemo.network

import cn.com.finaldemo.base.activity.IBaseView
import cn.com.finaldemo.network.BaseBean
import cn.com.finaldemo.utils.GmConstans
import cn.com.finaldemo.utils.GmConstans.deviceID
import cn.com.finaldemo.utils.GmConstans.deviceType
import cn.com.finaldemo.utils.NetUtil
import com.android.yzw.networklibrary.OkHttpManager
import com.android.yzw.networklibrary.OkHttpManager.sendRequest
import io.reactivex.Observable

/**
 * Create by yinzhengwei on 2020-03-13
 * @Function
 */
object BaseRequest {

    private fun addHeader() {
        //每次请求都需要提交
        OkHttpManager.headerMap.run {
            //登陆后将用户信息中的token更新到header中
            put("deviceType", deviceType)
            put("device-id", deviceID)
//            put("Access-Type", "Android/${BuildConfig.VERSION_NAME}/${BuildConfig.VERSION_CODE}")
        }
    }

    fun <T> send(
        observable: Observable<T>,
        mView: IBaseView? = null,
        successful: (T) -> Unit,
        fail: (BaseBean) -> Unit
    ) {
        if (!NetUtil.isNetworkAvailable(GmConstans.mInstance)) {
            fail(BaseBean(403, "网络异常，请稍后重试", false))
            return
        }

        mView?.showLading()
        //每次请求都需要提交
        addHeader()
        sendRequest(observable, {
            mView?.hiddenLading()
            val successBean = it as BaseBean
            if (successBean.success && successBean.code == 200) {
                successful(it)
            } else {
                //fail(it)
                fail(BaseBean(it.code, "获取数据失败，请稍后传重试", false))
            }
        }, {
            mView?.hiddenLading()
            fail(BaseBean(500, "获取数据失败，请稍后传重试", false))
        })
    }

    fun <T> send(
        observable: Observable<T>,
        successful: (T) -> Unit,
        fail: (BaseBean) -> Unit
    ) {
        if (!NetUtil.isNetworkAvailable(GmConstans.mInstance)) {
            fail(BaseBean(403, "网络异常，请稍后重试", false))
            return
        }
        //每次请求都需要提交
        addHeader()
        sendRequest(observable, {
            val successBean = it as BaseBean
            if (successBean.success && successBean.code == 200) {
                successful(it)
            } else {
                //fail(it)
                fail(BaseBean(it.code, "获取数据失败，请稍后传重试", false))
            }
        }, {
            fail(BaseBean(500, "获取数据失败，请稍后传重试", false))
        })
    }
}