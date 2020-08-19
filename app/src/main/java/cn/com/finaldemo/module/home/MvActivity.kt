package cn.com.finaldemo.module.home

import android.app.Activity
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.widget.MediaController
import android.widget.Toast
import cn.com.finaldemo.R
import kotlinx.android.synthetic.main.dialog_layout.*

/**
 * Create by yinzhengwei on 2020-08-06
 * @Function
 */
class MvActivity :Activity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_layout)

        val params = videoView.layoutParams
        params.width = resources.displayMetrics.widthPixels
//        params.height = resources.displayMetrics.heightPixels
        videoView.layoutParams = params

        //播放完成回调
        videoView.setOnCompletionListener(MyPlayerOnCompletionListener())
        videoView.setVideoURI(Uri.parse("android.resource://" + this.packageName + "/raw/qinghua"));
        //设置视频控制器
        videoView.setMediaController(MediaController(this))
//        videoView.setVideoPath("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4")
        videoView.start()
    }

    internal inner class MyPlayerOnCompletionListener : MediaPlayer.OnCompletionListener {

        override fun onCompletion(mp: MediaPlayer) {
            Toast.makeText(this@MvActivity, "播放完成了", Toast.LENGTH_SHORT).show()
        }
    }
}