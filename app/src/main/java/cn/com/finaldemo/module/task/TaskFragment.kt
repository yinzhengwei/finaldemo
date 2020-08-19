package cn.com.finaldemo.module.task

import cn.com.finaldemo.R
import cn.com.finaldemo.base.activity.BaseFragment
import cn.com.finaldemo.databinding.TaskFragmentBinding

class TaskFragment : BaseFragment<TaskFragmentBinding, TaskViewModel>() {

    override fun getLayoutId() = R.layout.task_fragment

    override fun createViewModel() = TaskViewModel(this)

    override fun initView() {
    }

    override fun loadData() {
    }

}
