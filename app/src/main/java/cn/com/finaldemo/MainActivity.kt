package cn.com.finaldemo

import android.content.Intent
import androidx.core.view.forEachIndexed
import cn.com.finaldemo.adapter.MyFragmentAdapter
import cn.com.finaldemo.base.activity.BaseActivity
import cn.com.finaldemo.module.home.HomeFragment
import cn.com.finaldemo.module.mine.MineFragment
import cn.com.finaldemo.module.task.TaskFragment
import cn.com.finaldemo.utils.ToastUtil
import cn.com.finaldemo.databinding.ActivityMainBinding
import com.yzw.permissiongranted.PermissionList
import com.yzw.permissiongranted.PermissionUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<ActivityMainBinding, MainActivityModel>() {

    override fun getLayoutId() = R.layout.activity_main

    override fun createViewModel() = MainActivityModel(this)

    override fun initView() {
        val list = listOf(HomeFragment(), TaskFragment(), MineFragment())
        val adapter = MyFragmentAdapter(list, supportFragmentManager)
        viewPager.offscreenPageLimit = 2
        viewPager.adapter = adapter

        bottom_navigation.forEachIndexed { index, view ->
            view.setOnClickListener {
               // viewPager.currentItem = index

                startActivity(Intent(this,TestActivity::class.java))
            }
        }
    }

    override fun loadData() {
//        ToastUtil.showToast(
//            PermissionUtils.isOpenPermisson(
//                this,
//                PermissionList.permission_sdcardRW
//            ).toString()
//        )
    }

}
