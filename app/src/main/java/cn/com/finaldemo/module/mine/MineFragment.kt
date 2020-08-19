package cn.com.finaldemo.module.mine

import cn.com.finaldemo.R
import cn.com.finaldemo.base.activity.BaseFragment
import cn.com.finaldemo.databinding.TaskFragmentBinding

class MineFragment : BaseFragment<TaskFragmentBinding, MineViewModel>() {

    override fun getLayoutId() = R.layout.mine_fragment

    override fun createViewModel() = MineViewModel(this)

    override fun initView() {
    }

    override fun loadData() {
    }

}
