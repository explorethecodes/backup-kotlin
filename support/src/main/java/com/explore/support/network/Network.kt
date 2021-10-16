package com.explore.support.network

import java.net.MalformedURLException
import java.net.URL

fun String.extractBaseUrl() : String {
    var baseUrl = ""
    try {
        val url = URL("http://test.example.com/abcd/test.html")
        baseUrl = url.protocol + "://" + url.host
    } catch (e: MalformedURLException) {
        // do something
    }
    return baseUrl
}