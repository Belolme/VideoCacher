package com.billin.www.videodownloader

import android.graphics.Bitmap
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient

/**
 * 可以缓存 MP4 视频文件的 client
 */
class CacheWebClient : WebViewClient() {

    private var mCacheVideoUrl: String? = null

    private val TAG = "CacheWebClient"

    override fun shouldOverrideUrlLoading(view: WebView, request: String): Boolean {
        Log.d(TAG, request)
        if (request.startsWith("http://")) {
            view.loadUrl(request)
            return true
        }

        return false
    }

    override fun onLoadResource(view: WebView, url: String) {
        if (url.contains("mp4")) {
            view.evaluateJavascript("document.getElementsByTagName('video')[0].currentSrc") {
                if (it != null && !it.isEmpty() && it != "null") {
                    mCacheVideoUrl = it.trim().trim('\"')
                }
            }
        }
        super.onLoadResource(view, url)
    }

    override fun onPageStarted(view: WebView, p1: String?, p2: Bitmap?) {
        view.evaluateJavascript("window.MediaSource = undefined") {}
        super.onPageStarted(view, p1, p2)
    }

    fun getCacheVideo(): String? {
        return mCacheVideoUrl
    }
}