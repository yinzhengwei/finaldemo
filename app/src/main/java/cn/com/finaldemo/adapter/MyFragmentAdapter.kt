package cn.com.finaldemo.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class MyFragmentAdapter(var list: List<Fragment>, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        val fragment = list[position]
        fragment.arguments = Bundle().apply { putString("position", position.toString()) }
        return fragment
    }

    override fun getCount() = list.size
}