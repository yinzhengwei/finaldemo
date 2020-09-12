package cn.com.finaldemo.base.activity

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Build
import android.os.Build.VERSION_CODES.LOLLIPOP_MR1
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import cn.com.finaldemo.R
import cn.com.finaldemo.base.viewmodel.BaseViewModel
import crossoverone.statuslib.StatusUtil

/**
 *  Created by yinzhengwei on 2020-02-05.
 *  @Function
 */
abstract class BaseActivity<T : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity(),
    IBaseView {

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun createViewModel(): VM

    abstract fun initView()

    abstract fun loadData()

    lateinit var mBinding: T
    lateinit var mViewModel: VM

    private val tag = "BaseActivity===" + this.javaClass.name+": "
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(tag, "onCreate")
        //竖屏
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        if (Build.VERSION.SDK_INT <= LOLLIPOP_MR1) {
            StatusUtil.setUseStatusBarColor(this, Color.BLACK)
            // 第二个参数是是否沉浸,第三个参数是状态栏字体是否为黑色。
            StatusUtil.setSystemStatus(this, true, false)
        } else {
            StatusUtil.setUseStatusBarColor(this, Color.TRANSPARENT)
            // 第二个参数是是否沉浸,第三个参数是状态栏字体是否为黑色。
            StatusUtil.setSystemStatus(this, true, true)
        }

        mBinding = DataBindingUtil.setContentView(this, getLayoutId());

        mViewModel = createViewModel()

        initView()

        loadData()

        //是否开发暗灰色
//        try {
//            val paint = Paint()
//            val colorMatrix = ColorMatrixColorFilter(ColorMatrix().apply { setSaturation(0.0f) })
//            paint.colorFilter = colorMatrix
//            window.decorView.setLayerType(View.LAYER_TYPE_HARDWARE, paint)
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(tag, "onRestart")
    }

    override fun onStart() {
        super.onStart()
        Log.d(tag, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(tag, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(tag, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(tag, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(tag, "onDestroy")
        mViewModel.cancelJob()
        unBinding()
    }

    override fun unBinding() {
        mBinding.unbind()
    }

    private var progressBar: AlertDialog? = null
    private var progressBarText: TextView? = null
    //展示loading
    fun showLading(msg: String) {
        if (progressBar != null && progressBar?.isShowing == true) {
            progressBarText?.text = msg
            return
        }

        if (isFinishing)
            return
        progressBar = ProgressDialog.show(this, msg, msg).apply {
            setCancelable(true)
            setCanceledOnTouchOutside(false)
            setContentView(R.layout.progress_layout)

            progressBarText = findViewById<TextView>(R.id.tv_msg)?.apply {
                text = msg
            }
        }
    }

    //重载方法！为了提供提示文案的动态可切换
    override fun showLading() {
        showLading("正在加载数据中...")
    }

    override fun hiddenLading() {
        try {
            if (progressBar != null && progressBar?.isShowing == true)
                progressBar?.dismiss()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setTitleCommon(viewGroup: ViewGroup, title: String) {
        setTitleRightCommon(viewGroup, title, null)
    }

    fun setTitleRightCommon(viewGroup: ViewGroup, title: String, rightText: String?) {
        viewGroup.getChildAt(0).setOnClickListener {
            finish()
        }
        (viewGroup.getChildAt(1) as TextView).text = title
        if (rightText != null)
            (viewGroup.getChildAt(2) as TextView).text = rightText
    }

    fun setTitleRightClickCommon(
        viewGroup: ViewGroup,
        title: String,
        rightText: String?,
        callback: () -> Unit
    ) {
        setTitleRightCommon(viewGroup, title, rightText)
        if (rightText != null) {
            (viewGroup.getChildAt(2) as TextView).setOnClickListener {
                callback()
            }
        }
    }


    fun <R> fetData(result: MutableLiveData<*>?, callback: (R?) -> Unit) {
        if (result == null) {
            callback(null)
        } else
            result.observe(this, Observer {
                callback(it as R)
            })
    }
}