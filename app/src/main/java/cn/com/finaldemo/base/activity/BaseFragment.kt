package cn.com.finaldemo.base.activity

import android.app.Activity
import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import cn.com.finaldemo.R
import cn.com.finaldemo.base.viewmodel.BaseViewModel

/**
 *  Created by yinzhengwei on 2020-02-05.
 *  @Function
 */
abstract class BaseFragment<T : ViewDataBinding, VM : BaseViewModel> : Fragment(), IBaseView {

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun createViewModel(): VM

    abstract fun initView()

    abstract fun loadData()

    var mBinding: T? = null
    var mViewModel: VM? = null

    //Fragment的View加载完毕的标记
    private var isViewCreated: Boolean = false
    //Fragment对用户可见的标记
    var isUIVisible: Boolean = false
    protected var isLoadCompleted: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)

        mViewModel = createViewModel()
        lifecycle.addObserver(mViewModel!!)

        //mViewModel = ViewModelProviders.of(this).get(createViewModel()::class.java)
        //mViewModel = defaultViewModelProviderFactory.create(createViewModel())

        mBinding?.executePendingBindings()

        isViewCreated = true

        return mBinding?.root
    }

    override fun setMenuVisibility(menuVisible: Boolean) {
        super.setMenuVisibility(menuVisible)
        Log.d(
            "BaseFragment",
            "${this.javaClass.name} ========= setUserVisibleHint  =========  ${menuVisible}  =========  ${isLoadCompleted}"
        )

        if (menuVisible && !isLoadCompleted) {
            isUIVisible = true
            lazyLoad()
        } else {
            isUIVisible = false
        }
    }

    override fun onResume() {
        super.onResume()

        if (isMenuVisible && !isLoadCompleted) {
            isUIVisible = true
            lazyLoad()
        }
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initView()

        if (userVisibleHint && !isLoadCompleted) {
            // 此处不需要判断isViewCreated，因为这个方法在onCreateView方法之后执行
            lazyLoad()
        }

        Log.d(
            "BaseFragment",
            "${this.javaClass.name} =========  onActivityCreated  =========  ${userVisibleHint}  =========  ${isLoadCompleted}"
        )
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        isUIVisible = !hidden
    }

    protected fun lazyLoad() {
        Log.d(
            "BaseFragment",
            "${this.javaClass.name} =========  lazyLoad  =========  ${isViewCreated}  =========  ${isUIVisible}"
        )

        //这里进行双重标记判断,是因为setUserVisibleHint会多次回调,并且会在onCreateView执行前回调,必须确保onCreateView加载完毕且页面可见,才加载数据
        if (isViewCreated && isUIVisible) {
            loadData()
            //数据加载完毕,恢复标记,防止重复加载
            isViewCreated = false;
            isUIVisible = false;
            isLoadCompleted = true
        } else {
            //loadDataEnd()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding?.unbind()
    }

    //展示loading
    private var progressBar: ProgressDialog? = null
    private var progressBarText: TextView? = null
    //展示loading
    fun showLading(msg: String) {
        if (progressBar != null && progressBar?.isShowing == true) {
            progressBarText?.text = msg
            return
        }

        if (context == null || (context as Activity).isFinishing)
            return
        progressBar = ProgressDialog.show(context, msg, msg).apply {
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

    //只设置title
    fun setTitle(viewGroup: ViewGroup, title: String) {
        viewGroup.getChildAt(0).visibility = View.GONE
        setTitleRightCommon(viewGroup, title)
    }

    //默认设置返回按钮事件和title
    fun setTitleCommon(viewGroup: ViewGroup, title: String) {
        setTitleRightCommon(viewGroup, title)
    }

    //设置返回按钮、title、右文案和点击事件
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

    private fun setTitleRightCommon(viewGroup: ViewGroup, title: String, rightText: String? = "") {
        viewGroup.getChildAt(0).setOnClickListener {
            // finish()
        }
        (viewGroup.getChildAt(1) as TextView).text = title
        if (rightText != null)
            (viewGroup.getChildAt(2) as TextView).text = rightText
    }

    fun <R> fetData(result: MutableLiveData<*>?, callback: (R?) -> Unit) {
        if (result == null) {
            callback(null)
        } else {
            if (view != null)
                result.observe(viewLifecycleOwner, Observer {
                    callback(it as R)
                })
        }
    }
}