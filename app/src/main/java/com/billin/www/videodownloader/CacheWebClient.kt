package com.billin.www.videodownloader

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient

/**
 * 可以缓存 MP4 视频文件的 client
 * todo 添加全屏展示
 * todo 添加前进后退页面按钮
 * todo 支持 m3u8 视频类型
 */
open class CacheWebClient : WebViewClient() {

    private var mCacheVideoUrl: String? = null

    private val TAG = "CacheWebClient"

    var videoCatchListener: ((view: WebView, videoUrl: String) -> Unit)? = null

    override fun shouldOverrideUrlLoading(view: WebView, request: String): Boolean {
        Log.d(TAG, request)
        val scheme = Uri.parse(request).scheme
        if (scheme != null && (scheme == "http" || scheme == "https")) {
            view.loadUrl(request)
            return true
        }

        return false
    }

    override fun onLoadResource(view: WebView, url: String) {
        Log.d(TAG, url)
        if (url.contains("mp4")) {
            view.evaluateJavascript("document.getElementsByTagName('video')[0].currentSrc") {
                if (it != null && !it.isEmpty() && it != "null") {
                    mCacheVideoUrl = it.trim().trim('\"')

                    if (videoCatchListener != null)
                        videoCatchListener?.invoke(view, mCacheVideoUrl!!)
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