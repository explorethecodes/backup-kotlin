package com.explore.support.modules.videos.videoplayer

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.explore.support.databinding.WidgetVideoPlayerBinding
import com.explore.support.utils.context.lifecycleOwner
import kotlinx.android.synthetic.main.widget_video_player.view.*

class VideoPlayerWidget : LinearLayout {

    private var videoPlayerWidgetListener: VideoPlayerWidgetListener? = null
    lateinit var video : Video

    private var binding: WidgetVideoPlayerBinding

    private var sampleUrl = "https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4"

    fun setData(video: Video){
        this.video = video
        context.lifecycleOwner()?.lifecycle?.addObserver(object : LifecycleObserver{
            @OnLifecycleEvent(Lifecycle.Event.ON_START)
            fun onStart(){
                if (simpleExoplayer == null){
                    initializePlayer()
                }
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
            fun onCreate(){
                initializePlayer()
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
            fun onPause(){
                simpleExoplayer?.let {
                    playVideo(false)
                }
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
            fun onResume(){
                simpleExoplayer?.let {
                    playVideo(false)
                }
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
            fun onStop(){
                simpleExoplayer?.let {
                    releasePlayer()
                }
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun onDestroy(){
                releasePlayer()
            }
        })
    }
    constructor(context: Context,video: Video) : this(context, null){
        setData(video)
    }
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
            context,
            attrs,
            defStyleAttr
    ) {

        binding = WidgetVideoPlayerBinding.inflate(
                LayoutInflater.from(context),
                this,
                true
        )
    }

    fun setListener (videoPlayerWidgetListener: VideoPlayerWidgetListener){
        this.videoPlayerWidgetListener = videoPlayerWidgetListener
    }

    private var simpleExoplayer: SimpleExoPlayer? = null
    private var playbackPosition: Long = 0

    private val dataSourceFactory: DataSource.Factory by lazy {
        DefaultDataSourceFactory(this.context, "exoplayer-sample")
    }

    private fun initializePlayer() {
        simpleExoplayer = SimpleExoPlayer.Builder(this.context).build()
        if (video.uri!=null){
            val mediaSource = buildMediaSource(video.uri!!, "*")
            simpleExoplayer?.prepare(mediaSource)
        } else{
            val uri = Uri.parse(video.url)
            val mediaSource = buildMediaSource(uri,"*")
            simpleExoplayer?.prepare(mediaSource)
        }

        exoplayerView.player = simpleExoplayer
        simpleExoplayer?.seekTo(playbackPosition)
        playVideo(false)
        simpleExoplayer?.addListener(object  : Player.EventListener{

            override fun onPlayerError(error: ExoPlaybackException) {
                // handle error
                println()
            }

            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                if (playbackState == Player.STATE_BUFFERING)
                    progressBar.visibility = View.VISIBLE
                else if (playbackState == Player.STATE_READY || playbackState == Player.STATE_ENDED)
                    progressBar.visibility = View.INVISIBLE
            }

        })
    }

    private fun playVideo(play : Boolean){
        simpleExoplayer?.let {
            playbackPosition = it.currentPosition
        }
        simpleExoplayer?.playWhenReady = play
    }

    private fun buildMediaSource(uri: Uri, type: String): MediaSource {
        return if (type == "dash") {
            DashMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(uri)
        } else {
            ProgressiveMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(uri)
        }
    }

    private fun releasePlayer() {
        simpleExoplayer?.playWhenReady = false
        simpleExoplayer?.let {
            playbackPosition = it.currentPosition
        }
        simpleExoplayer?.release()
    }

}

interface VideoPlayerWidgetListener{
    fun onChangeVideo()
    fun onDeleteVideo(videoPlayerWidget: VideoPlayerWidget)
}

data class Video(
        val uri: Uri?,
        val url: String?
)