package com.oneindia.journovideos.base.network.call

import android.util.Log
import com.oneindia.journovideos.base.network.exceptions.ApiException
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

abstract class NetworkCall {

    suspend fun<T: Any> apiRequest(call: suspend () -> Response<T>) : T{
        val response = call.invoke()
        if(response.isSuccessful){
            Log.d("ncm RESPONSE",response.body().toString())
            val responseBody = response.body()
            return response.body()!!
        }else{
            val error = response.errorBody()?.string()

            Log.d("ncm RESPONSE",error.toString())

            val message = StringBuilder()
            error?.let{
                try{
                    message.append(JSONObject(it).getString("message"))
                }catch(e: JSONException){ }
                message.append("\n")
            }
            message.append("Error Code: ${response.code()}")
            throw ApiException(message.toString())
        }
    }
}

interface NetworkCallListener {
    fun onNetworkCallStarted(callCode: CallCode)
    fun onNetworkCallSuccess(message: String, callCode: CallCode)
    fun onNetworkCallFailure(message: String, callCode: CallCode)
    fun onNetworkCallCancel(callCode: CallCode)
}

enum class CallCode {
    ANY,
    LOGIN,
    PROFILE,
    FORGOT_PASSWORD,
    USER_LOCATION_UPDATE,
    UPDATE_FCM_TOKEN,
    CREATE_JOB_INPUTS,
    SUGGESTED_PRODUCERS,
    CREATE_JOB,
    VIEW_JOB,
    ACTION_JOB,
    ATTEMPT_JOB_INPUTS,
    ATTEMPT_JOB,
    DETAILED_RATING,
    DETAILED_RATING_RULES,
    REPORTS,
    LOCATIONS
}

sealed class CallCodes

object ANY : CallCodes()
object LOGIN : CallCodes()