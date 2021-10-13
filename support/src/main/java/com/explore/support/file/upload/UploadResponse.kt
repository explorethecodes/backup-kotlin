package com.explore.support.file.upload

data class UploadResponse(
    var message: String? = null,
    var url: String? = null,
    var video_upload_path: String? = "",
    var image_path: String? = ""
)