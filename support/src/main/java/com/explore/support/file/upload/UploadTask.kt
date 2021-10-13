package com.explore.support.file.upload

import android.os.AsyncTask
import io.tus.java.client.TusClient
import io.tus.java.client.TusUpload
import io.tus.java.client.TusUploader
import java.net.URL

class UploadTask(
    client: TusClient,
    upload: TusUpload,
    private val trackUpload: TrackUpload
    ) :
        AsyncTask<Void?, Long?, URL?>() {
        private val client: TusClient
        private val upload: TusUpload
        private var exception: Exception? = null
        override fun onPreExecute() {
            trackUpload.uploading(0)
            trackUpload.uploadStarted()
//            activity.setStatus("Upload selected...")
//            activity.setPauseButtonEnabled(true)
//            activity.setUploadProgress(0)
        }

        override fun onPostExecute(uploadURL: URL?) {
            trackUpload.uploadSuccess(UploadResponse())
//            activity.setStatus(
//                """
//                    Upload finished!
//                    ${uploadURL.toString()}
//                    """.trimIndent()
//            )
//            activity.setPauseButtonEnabled(false)
        }

        override fun onCancelled() {
            trackUpload.uploadCancelled()
//            if (exception != null) {
//                activity.showError(exception!!)
//            }
//            activity.setPauseButtonEnabled(false)
        }

        override fun onProgressUpdate(vararg updates: Long?) {
            val uploadedBytes = updates[0]
            val totalBytes = updates[1]
            trackUpload.uploading((uploadedBytes!!.toDouble() / totalBytes!! * 100).toInt())
//            activity.setStatus(
//                "Uploaded " + (uploadedBytes!!.toDouble() / totalBytes!! * 100).toInt() + "% | " + String.format(
//                    "%d/%d.",
//                    uploadedBytes,
//                    totalBytes
//                )
//            )
//            activity.setUploadProgress((uploadedBytes.toDouble() / totalBytes * 100).toInt())
        }

        override fun doInBackground(vararg params: Void?): URL? {
            try {
                val uploader: TusUploader = client.resumeOrCreateUpload(upload)
                val totalBytes: Long = upload.getSize()
                var uploadedBytes: Long = uploader.getOffset()

                // Upload file in 1MiB chunks
                uploader.setChunkSize(1024 * 1024)
                while (!isCancelled && uploader.uploadChunk() > 0) {
                    uploadedBytes = uploader.getOffset()
                    publishProgress(uploadedBytes, totalBytes)
                }
                uploader.finish()
                return uploader.getUploadURL()
            } catch (e: Exception) {
                exception = e
                cancel(true)
            }
            return null
        }

        init {
            this.client = client
            this.upload = upload
        }
    }

interface TrackUpload {
    fun uploadStarted()
    fun uploadCancelled()
    fun uploading(progress : Int)
    fun uploadSuccess(uploadResponse: UploadResponse)
    fun uploadFailed(message : String)
}