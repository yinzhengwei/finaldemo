package cn.com.finaldemo.module.home

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.com.finaldemo.R
import cn.com.finaldemo.base.activity.BaseFragment
import cn.com.finaldemo.databinding.TaskFragmentBinding
import cn.com.finaldemo.utils.ToastUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.home_fragment.*

class HomeFragment : BaseFragment<TaskFragmentBinding, HomeViewModel>() {

    override fun getLayoutId() = R.layout.home_fragment

    override fun createViewModel() = HomeViewModel(this)

    override fun initView() {
        if (isMenuVisible && arguments != null && arguments?.get("position") == "0") {
            recycler.postDelayed({
                initData()
            }, 200)
        }
    }

    override fun loadData() {
    }

    override fun setMenuVisibility(menuVisible: Boolean) {
        super.setMenuVisibility(menuVisible)
        if (recycler != null) {
            if (menuVisible) {
                initData()
            } else {
                if (recycler.adapter != null)
                    (recycler.adapter as MyAdapter).setNewData(mutableListOf())
                videoView.pause()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (!isMenuVisible) return
        videoView.start()
        videoView?.postDelayed({
            videoView?.seekTo(videoView.duration / 2)
        }, 100)
    }

    private fun initData() {
        if (!isMenuVisible) return
        tv_open_mv.setOnClickListener {
            showMyStyle()
        }
        initAnim(recycler)

        val list = listOf("1", "2", "3", "4")
        if (recycler.adapter != null) {
            (recycler.adapter as MyAdapter).setNewData(list)

            videoView.seekTo(videoView.duration / 2)
            return
        }
        recycler.layoutManager = LinearLayoutManager(activity)
        recycler.adapter = MyAdapter().apply {
            setNewData(list)

            setOnItemClickListener { _, _, position ->
                if (position == 1) {
                    ToastUtil.showToast("恭喜你，答对了！")
                } else {
                    ToastUtil.showToast("不对哦，继续努力！")
                }
            }
        }

        //播放完成回调
        videoView.setOnCompletionListener(MyPlayerOnCompletionListener())
        videoView.setVideoURI(Uri.parse("android.resource://" + activity?.packageName + "/raw/qinghua"));

        onResume()
    }

    internal inner class MyPlayerOnCompletionListener : MediaPlayer.OnCompletionListener {

        override fun onCompletion(mp: MediaPlayer) {
            Toast.makeText(activity, "播放完成了", Toast.LENGTH_SHORT).show()
        }
    }

    class MyAdapter : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_layout) {
        override fun convert(helper: BaseViewHolder, item: String?) {
            when (item) {
                "1" -> helper.setText(R.id.tv, "弱水三千")
                "2" -> helper.setText(R.id.tv, "青花")
                "3" -> helper.setText(R.id.tv, "冬天的秘密")
                "4" -> helper.setText(R.id.tv, "黄昏")
            }
        }
    }

    fun initAnim(recycler: RecyclerView) {
        val animation = AnimationUtils.loadAnimation(activity, R.anim.left)
        val controller = LayoutAnimationController(animation)
        controller.order = LayoutAnimationController.ORDER_NORMAL
        controller.delay = 0.2f

        recycler.layoutAnimation = controller
    }

    /**
     * 原生自定义 dialog
     */
    private fun showMyStyle() {
        videoView.pause()

        startActivity(Intent(activity, MvActivity::class.java))
    }

}
