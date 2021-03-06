package com.explore.support.upload

import android.net.Uri
import androidx.fragment.app.FragmentActivity
import com.explore.support.file.getFileName
import com.explore.support.network.retrofit.Api
import com.explore.support.upload.retrofit.UploadRequest
import com.explore.support.upload.retrofit.UploadResponse
import com.oneindia.journovideos.base.network.interceptor.NetworkInterceptor
import io.tus.android.client.TusAndroidUpload
import io.tus.android.client.TusPreferencesURLStore
import io.tus.java.client.TusClient
import io.tus.java.client.TusUpload
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.net.URL
import java.util.concurrent.TimeUnit

interface UploadTracker {
    fun onUploadStarted(uploadType: UploadType)
    fun onUploadCancelled(uploadType: UploadType)
    fun onUploading(percentage : Int, uploadType: UploadType)
    fun onUploadSuccess(uploadResponse: UploadResponse, uploadType: UploadType)
    fun onUploadFailed(message: String, uploadType: UploadType)
}

enum class UploadType {
    FILE,
    VIDEO,
    IMAGE
}

fun getContentType(uploadType: UploadType): String {

    var contentType : String
    when(uploadType){
        UploadType.FILE -> contentType = "file"
        UploadType.VIDEO -> contentType = "video"
        UploadType.IMAGE -> contentType = "image"
    }

    return contentType
}

fun FragmentActivity.uploadFile(uploadUri : Uri, uploadUrl: String, uploadType: UploadType, uploadTracker: UploadTracker) {

    val parcelFileDescriptor = this.contentResolver.openFileDescriptor(uploadUri, "r", null) ?: return

    val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
    val file = File(cacheDir, contentResolver.getFileName(uploadUri))
    val outputStream = FileOutputStream(file)
    inputStream.copyTo(outputStream)

    val body = UploadRequest(file, uploadType, object : UploadTracker {
        override fun onUploadStarted(uploadType: UploadType) {
        }

        override fun onUploadCancelled(uploadType: UploadType) {
        }

        override fun onUploading(percentage: Int, uploadType: UploadType) {
            uploadTracker.onUploading(percentage,uploadType)
        }

        override fun onUploadSuccess(uploadResponse: UploadResponse, uploadType: UploadType) {
        }

        override fun onUploadFailed(message: String, uploadType: UploadType) {
        }

    })

    val networkInterceptor = NetworkInterceptor(this)
    val uploadApi = Api(networkInterceptor,uploadUrl)

    val uploadCall = uploadApi.upload(
        MultipartBody.Part.createFormData(
            "file",
            file.name,
            body
        ),
        RequestBody.create(MediaType.parse("multipart/form-data"), getContentType(uploadType)),
        uploadUrl
    )

    try {
        uploadCall.enqueue(object : Callback<UploadResponse> {
            override fun onFailure(call: Call<UploadResponse>, t: Throwable) {
                if(call.isCanceled){
                    uploadTracker.onUploadCancelled(uploadType)
                } else {
                    uploadTracker.onUploadFailed(t.message!!,uploadType)
                }
            }

            override fun onResponse(
                call: Call<UploadResponse>,
                response: Response<UploadResponse>
            ) {
                if(response.isSuccessful){
                    response.body()?.let {
                        uploadTracker.onUploadSuccess(it,uploadType)
                    }
                }else{
                    val error = response.errorBody()?.string()
                    val message = StringBuilder()
                    error?.let{
                        try{
                            message.append(JSONObject(it).getString("message"))
                        }catch(e: JSONException){ }
                        message.append("\n")
                    }
                    message.append("Error Code: ${response.code()}")
                    val tempMessage = "Something went wrong, Try to upload video again !"
                    uploadTracker.onUploadFailed(tempMessage,uploadType)
                }
            }
        })
    } catch (e:Exception){
        uploadTracker.onUploadFailed(e.toString(),uploadType)
    }
}
fun FragmentActivity.uploadFileResumable(uploadUri : Uri, uploadUrl: String, uploadType: UploadType, uploadTracker: UploadTracker){


    try {
        val pref = getSharedPreferences("tus", 0)
        val client = TusClient()

        client.setUploadCreationURL(URL(uploadUrl))
        client.enableResuming(TusPreferencesURLStore(pref))

        try {
            val upload: TusUpload = TusAndroidUpload(uploadUri, this)
            val uploadTask = UploadTask(client, upload, uploadType, object : UploadTracker{
                override fun onUploadStarted(uploadType: UploadType) {
                    uploadTracker.onUploadStarted(uploadType)
                }

                override fun onUploadCancelled(uploadType: UploadType) {
                    uploadTracker.onUploadCancelled(uploadType)
                }

                override fun onUploading(percentage: Int, uploadType: UploadType) {
                    uploadTracker.onUploading(percentage,uploadType)
                }

                override fun onUploadSuccess(
                    uploadResponse: UploadResponse,
                    uploadType: UploadType
                ) {
                    uploadTracker.onUploadSuccess(uploadResponse,uploadType)
                }

                override fun onUploadFailed(message: String, uploadType: UploadType) {
                    uploadTracker.onUploadFailed(message,uploadType)
                }
            })
            uploadTask.execute(*arrayOfNulls<Void>(0))
        } catch (e: Exception) {
            uploadTracker.onUploadFailed(e.toString(),UploadType.VIDEO)
        }

    } catch (e: Exception) {


    }
}