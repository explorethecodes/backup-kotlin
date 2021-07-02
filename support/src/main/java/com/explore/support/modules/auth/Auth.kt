package com.explore.support.modules.auth

import android.util.Base64
import com.bumptech.glide.load.model.LazyHeaderFactory

class BasicAuth(private val auth: Auth) :
    LazyHeaderFactory {
    override fun buildHeader(): String? {
        return getAuthValue(auth)
    }

}

fun getAuthValue(auth: Auth) : String {
    return "Basic " + Base64.encodeToString("${auth.username}:${auth.password}".toByteArray(), Base64.NO_WRAP)
}

data class Auth(
    val username: String,
    val password: String
)