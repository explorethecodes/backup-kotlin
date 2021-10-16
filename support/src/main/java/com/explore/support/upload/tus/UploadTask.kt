package com.explore.support.upload

import android.os.AsyncTask
import com.explore.support.upload.retrofit.UploadResponse
import io.tus.java.client.TusClient
import io.tus.java.client.TusUpload
import io.tus.java.client.TusUploader
import java.net.URL

class UploadTask(
    client: TusClient,
    upload: TusUpload,
    private val uploadType: UploadType,
    private val uploadTracker: UploadTracker
    ) :
        AsyncTask<Void?, Long?, URL?>() {
        private val client: TusClient
        private val upload: TusUpload
        private var exception: Exception? = null
        override fun onPreExecute() {
            uploadTracker.onUploading(0, uploadType)
            uploadTracker.onUploadStarted(uploadType)
        }

        override fun onPostExecute(uploadURL: URL?) {
            uploadTracker.onUploadSuccess(UploadResponse(),uploadType)
        }

        override fun onCancelled() {
            uploadTracker.onUploadCancelled(uploadType)
        }

        override fun onProgressUpdate(vararg updates: Long?) {
            val uploadedBytes = updates[0]
            val totalBytes = updates[1]
            uploadTracker.onUploading(
                (uploadedBytes!!.toDouble() / totalBytes!! * 100).toInt(),
                uploadType
            )
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