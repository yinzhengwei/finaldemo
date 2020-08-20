package cn.com.finaldemo.widget

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Build
import android.util.AttributeSet
import android.webkit.CookieManager
import android.webkit.WebSettings
import android.webkit.WebView
import cn.com.finaldemo.utils.NetUtil
import java.io.File

/**
 *  Created by yinzhengwei on 2020-03-14.
 *  @Function
 */
class CustomWebView constructor(context: Context, attrs: AttributeSet? = null) :
    WebView(getFixedContext(context), attrs) {

    init {
        //初始化webview设置信息
        this.isVerticalScrollBarEnabled = true
        this.isHorizontalScrollBarEnabled = false

        settings?.run {
            /**
             * setAllowFileAccess 启用或禁止WebView访问文件数据 setBlockNetworkImage 是否显示网络图像
             * setBuiltInZoomControls 设置是否支持缩放 setCacheMode 设置缓冲的模式
             * setDefaultFontSize 设置默认的字体大小 setDefaultTextEncodingName 设置在解码时使用的默认编码
             * setFixedFontFamily 设置固定使用的字体 setJavaSciptEnabled 设置是否支持Javascript
             * setLayoutAlgorithm 设置布局方式 setLightTouchEnabled 设置用鼠标激活被选项
             * setSupportZoom 设置是否支持变焦
             */
            // blockNetworkImage = true
            lightTouchEnabled = true

            setSupportZoom(true)
            builtInZoomControls = true// 隐藏缩放按钮

            //打开网页自动播放视频
            mediaPlaybackRequiresUserGesture = false

            // 启用地理定位,该方法需要配合在WebChromeClient中的onGeolocationPermissionsShowPrompt()回调方法才能起效果
            setGeolocationEnabled(true)

            CookieManager.getInstance().setAcceptThirdPartyCookies(this@CustomWebView, true)

            //使用系统浏览器下载
//            setDownloadListener { url, userAgent, contentDisposition, mimeType, contentLength ->
//                val intent = Intent()
//                intent.action = Intent.ACTION_VIEW
//                intent.data = Uri.parse(url)
//                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//                context?.startActivity(intent)
//            }

            //自适应屏幕
            layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS// 排版适应屏幕
//            layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
            // setUseWideViewPort方法设置webview推荐使用的窗口。setLoadWithOverviewMode方法是设置webview加载的页面的模式。
            useWideViewPort = true// 可任意比例缩放
            loadWithOverviewMode = true
            savePassword = true
            saveFormData = true// 保存表单数据
            javaScriptEnabled = true
            setAppCachePath(
                File(
                    context.cacheDir.absolutePath,
                    "databases"
                ).absolutePath
            )// 设置定位的数据库路径
            domStorageEnabled = true
            setAppCacheEnabled(true)
            //设置缓存
            cacheMode = if (!NetUtil.isNetworkAvailable(context)) {
                WebSettings.LOAD_CACHE_ELSE_NETWORK
            } else {
                WebSettings.LOAD_DEFAULT
            }
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                // Enable remote debugging via chrome://inspect
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    setWebContentsDebuggingEnabled(true)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

private fun getFixedContext(context: Context?): Context? {
    try {
        val resources = context?.resources ?: return context
        val configuration = resources.configuration

        if (Build.VERSION.SDK_INT in 21..22)
            try {
                return context.createConfigurationContext(Configuration())
            } catch (e: Exception) {
            }
        else if (Build.VERSION.SDK_INT >= 25) {
            try {
                val mContext = context.applicationContext.createConfigurationContext(configuration)
                return mContext.createConfigurationContext(configuration)
            } catch (e: Exception) {
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return context
}
