package com.explore.support.upload.retrofit

import android.os.Handler
import android.os.Looper
import com.explore.support.upload.UploadTracker
import com.explore.support.upload.UploadType
import okhttp3.MediaType
import okhttp3.RequestBody
import okio.BufferedSink
import java.io.File
import java.io.FileInputStream

class UploadRequest(
    private val file: File,
    private val uploadType: UploadType,
    private val uploadTracker: UploadTracker,
) : RequestBody() {

    fun getContentType(): String {

        var contentType : String
        when(uploadType){
            UploadType.FILE -> contentType = "file"
            UploadType.VIDEO -> contentType = "video"
            UploadType.IMAGE -> contentType = "image"
        }

        return contentType
    }

    override fun contentType() = MediaType.parse("${getContentType()}/*")

    override fun contentLength() = file.length()

    override fun writeTo(sink: BufferedSink) {
        val length = file.length()
        val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
        val fileInputStream = FileInputStream(file)
        var uploaded = 0L
        fileInputStream.use { inputStream ->
            var read: Int
            val handler = Handler(Looper.getMainLooper())
            while (inputStream.read(buffer).also { read = it } != -1) {
                handler.post(ProgressUpdater(uploaded, length))
                uploaded += read
                sink.write(buffer, 0, read)
            }
        }
    }

    inner class ProgressUpdater(
        private val uploaded: Long,
        private val total: Long
    ) : Runnable {
        override fun run() {
            uploadTracker.onUploading((100 * uploaded / total).toInt(),uploadType)
        }
    }

    companion object {
        private const val DEFAULT_BUFFER_SIZE = 2048
    }
}