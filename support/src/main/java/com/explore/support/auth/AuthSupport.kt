package com.explore.support.auth

import android.util.Base64
import com.bumptech.glide.load.model.LazyHeaderFactory

data class Auth(
        val username: String,
        val password: String
)

class BasicAuth(private val auth: Auth) :
    LazyHeaderFactory {
    override fun buildHeader(): String? {
        return getBasicAuth(auth)
    }

}

fun getBasicAuth(auth: Auth) : String {
    return "Basic " + Base64.encodeToString("${auth.username}:${auth.password}".toByteArray(), Base64.NO_WRAP)
}