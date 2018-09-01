package com.billin.www.videodownloader

import android.support.v7.app.AppCompatActivity
import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"

        const val YOUTUBE_H5_TEST = "https://www.youtube.com/html5"

        const val YOUTUBE = "https://www.youtube.com/watch?v=MuavmqfOZ6U&start_radio=1&list=RDMuavmqfOZ6U"

        const val IQIYI = "http://www.iqiyi.com/v_19rqzasy98.html?list=19rrm7rlsi"

        const val YOUKU = "https://m.youku.com/video/id_XMzcxNzIzNDQwMA==.html?spm=..m_205971.5~5%212~5~5~5%215~5~5~A"

        const val VIMEO = "https://vimeo.com/287383546"

        const val NETEAST = "https://music.163.com/#/mv?id=5481577"

        const val X5_TEST = "http://soft.imtt.qq.com/browser/tes/feedback.html"

        const val IPHONE_UA = "Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1"

        const val ANDROID_UA = "Mozilla/5.0 (Linux; Android 5.1; m2 note Build/LMY47D; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/44.0.2403.147 Mobile Safari/537.36"

        const val IE_UA = "Mozilla/5.0(Linux; MSIE 9.0; m2 note Build/LMY47D; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/37.0.0.0 Mobile Safari/537.36"
    }


    val mWebView: WebView by lazy { findViewById<WebView>(R.id.webview) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        @SuppressLint("SetJavaScriptEnabled")
        mWebView.settings.javaScriptEnabled = true
        mWebView.settings.mediaPlaybackRequiresUserGesture = false
        mWebView.settings.allowContentAccess = true
        mWebView.settings.pluginState = WebSettings.PluginState.ON
        mWebView.settings.domStorageEnabled = true;
        mWebView.webChromeClient = WebChromeClient()

        mWebView.webViewClient = object : WebViewClient() {

            override fun onLoadResource(view: WebView?, url: String) {
                if (url.contains("mp4")) {
                    Log.d(TAG, "url: $url")

                    mWebView.evaluateJavascript("document.getElementsByTagName('video')[0].currentSrc") {
                        Log.d(TAG, "onLoadResource: video current url is $it")
                    }
                }
                super.onLoadResource(view, url)
            }

            override fun onPageStarted(p0: WebView?, p1: String?, p2: Bitmap?) {
                Log.d(TAG, "onPageStart: &p1")
                mWebView.evaluateJavascript("window.MediaSource = undefined") {
                    Log.d(TAG, "evaluateJavascript $it")
                }
                super.onPageStarted(p0, p1, p2)
            }

            override fun onPageFinished(p0: WebView?, p1: String?) {
                Log.d(TAG, "onpageFinish: $p1")
                super.onPageFinished(p0, p1)
            }
        }

        mWebView.loadUrl(VIMEO)
    }
}