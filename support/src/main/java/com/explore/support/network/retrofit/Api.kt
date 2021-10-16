package com.explore.support.network.retrofit

import com.explore.support.network.extractBaseUrl
import com.explore.support.upload.retrofit.UploadResponse
import com.oneindia.journovideos.base.network.interceptor.NetworkInterceptor
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

interface Api {

    //---------------------------------------- UPLOAD -------------------------------------------//
    @Multipart
    @POST
    fun upload(
            @Part file : MultipartBody.Part,
            @Part("type") desc: RequestBody,
            @Url uploadUrl: String,
    ): Call<UploadResponse>

    companion object{
        operator fun invoke(
                networkInterceptor: NetworkInterceptor,
                uploadUrl: String
        ) : Api{

            val okkHttpclient = OkHttpClient.Builder()
                    .readTimeout(10000, TimeUnit.SECONDS)
                    .connectTimeout(10000, TimeUnit.SECONDS)
                    .writeTimeout(10000, TimeUnit.SECONDS)
                    .addInterceptor(networkInterceptor)
                    .build()

            return Retrofit.Builder()
                .client(okkHttpclient)
                .baseUrl(uploadUrl.extractBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Api::class.java)
        }
    }
}