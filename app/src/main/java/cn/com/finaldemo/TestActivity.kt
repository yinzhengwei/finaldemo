package cn.com.finaldemo

import cn.com.finaldemo.base.activity.BaseActivity
import cn.com.finaldemo.databinding.ActivityMainBinding

/**
 * Create by yinzhengwei on 2020-09-12
 * @Function
 */
class TestActivity: BaseActivity<ActivityMainBinding, MainActivityModel>() {

    override fun getLayoutId() = R.layout.activity_main

    override fun createViewModel() = MainActivityModel(this)


    override fun initView() {
    }

    override fun loadData() {
    }


}