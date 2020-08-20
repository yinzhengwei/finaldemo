package cn.com.finaldemo.module.home

import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.com.finaldemo.R
import cn.com.finaldemo.base.activity.BaseFragment
import cn.com.finaldemo.databinding.TaskFragmentBinding
import cn.com.finaldemo.utils.ToastUtil
import cn.com.finaldemo.utils.launch
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.home_fragment.*

class HomeFragment : BaseFragment<TaskFragmentBinding, HomeViewModel>() {

    //临时地址，测试时需要替换，不然播放会报错
    val path =
        "http://music.163.com/song/media/outer/url?id=190072.mp3"
    var mediaPlayer = MediaPlayer()

    override fun getLayoutId() = R.layout.home_fragment

    override fun createViewModel() = HomeViewModel(this)

    override fun initView() {
        initData()
    }

    override fun loadData() {
    }

    override fun setMenuVisibility(menuVisible: Boolean) {
        super.setMenuVisibility(menuVisible)
        if (recycler != null) {
            if (menuVisible) {
                if (!mediaPlayer.isPlaying)
                    mediaPlayer.start()
            } else {
                handler.removeMessages(0)

                if (mediaPlayer.isPlaying)
                    mediaPlayer.pause()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (!mediaPlayer.isPlaying)
            mediaPlayer.start()
    }

    override fun onPause() {
        super.onPause()
        handler.removeMessages(0)

        if (mediaPlayer.isPlaying)
            mediaPlayer.pause()
    }

    private fun initData() {
        initAnim(recycler)

        val list = listOf("1", "2", "3", "4")
        if (recycler.adapter != null) {
            (recycler.adapter as MyAdapter).setNewData(list)

            mediaPlayer.seekTo(mediaPlayer.duration / 2)
            return
        }
        recycler.layoutManager = LinearLayoutManager(activity)
        val adapter = MyAdapter().apply {
            setOnItemClickListener { _, _, position ->
                if (position == 1) {
                    ToastUtil.showToast("恭喜你，答对了！")
                } else {
                    ToastUtil.showToast("不对哦，继续努力！")
                }
            }
        }
        recycler.adapter = adapter
        adapter.setNewData(list)

        play()
    }

    private val handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)

            mediaPlayer.seekTo((mediaPlayer.duration / 2))

            sendEmptyMessageDelayed(0, 10_000)
        }
    }

    private fun play() {
        mediaPlayer = MediaPlayer()
        mediaPlayer.setDataSource(path)
        mediaPlayer.prepare()
        mediaPlayer.isLooping = true
        mediaPlayer.setOnPreparedListener {
            handler.sendEmptyMessage(0)
        }
        mediaPlayer.setOnErrorListener { mediaPlayer, _, i ->
            ToastUtil.showToast("播放出错")
            true
        }

        mediaPlayer.start()
    }

    internal inner class MyPlayerOnCompletionListener : MediaPlayer.OnCompletionListener {

        override fun onCompletion(mp: MediaPlayer) {
            ToastUtil.showToast("播放完成了")
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

    override fun onDestroyView() {
        super.onDestroyView()
        mediaPlayer.release()
    }

}
