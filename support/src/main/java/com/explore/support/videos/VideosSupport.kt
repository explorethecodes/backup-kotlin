package com.explore.support.videos

import android.net.Uri
import com.explore.support.videos.exoplayer.ExoPlayerWidget

data class Video(
        val uri: Uri?,
        val url: String?
)

interface VideoPlayerWidgetListener{
    fun onChangeVideo()
    fun onDeleteVideo(exoPlayerWidget: ExoPlayerWidget)
}