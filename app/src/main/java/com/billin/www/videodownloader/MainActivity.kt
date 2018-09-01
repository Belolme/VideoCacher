package com.billin.www.videodownloader

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {

    private companion object {
        const val TAG = "MainActivity"

        const val HAO_123 = "http://v.hao123.baidu.com/"

        const val YOUTUBE_H5_TEST = "https://www.youtube.com/html5"

        const val YOUTUBE = "https://www.youtube.com/watch?v=MuavmqfOZ6U&start_radio=1&list=RDMuavmqfOZ6U"

        const val IQIYI = "http://www.iqiyi.com/v_19rqzasy98.html?list=19rrm7rlsi"

        const val YOUKU = "https://m.youku.com"

        const val YOUKU_MP4 = "http://vali-dns.cp31.ott.cibntv.net/697638906E33D7186930736D4/03000807005B170F0B6D78932BA9DF48A6D4D3-2354-4782-BEDE-BFF956BDACD0.mp4?ccode=0501&duration=395&expire=18000&psid=9e672dcc136427e7a1022d1578a31edc&sp=500&ups_client_netip=7417380c&ups_ts=1535800792&ups_userid=&utid=SyYRFMKIaVACAdrVljziBAfv&vid=XMzY0NzQxNDQxNg%3D%3D&vkey=B2bdf3cb26c9b436aaefc36d46d21a29d&s=3b99719cb6014d728c8d"

        const val VIMEO = "https://vimeo.com/287383546"

        const val NETEAST = "https://music.163.com/#/mv?id=5481577"

        const val X5_TEST = "http://soft.imtt.qq.com/browser/tes/feedback.html"

        const val IPHONE_UA = "Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1"

        const val ANDROID_UA = "Mozilla/5.0 (Linux; Android 5.1; m2 note Build/LMY47D; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/44.0.2403.147 Mobile Safari/537.36"

        const val IE_UA = "Mozilla/5.0(Linux; MSIE 9.0; m2 note Build/LMY47D; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/37.0.0.0 Mobile Safari/537.36"
    }

    private val mWebView: WebView by lazy { findViewById<WebView>(R.id.main_webview) }

    private val mInputView: EditText by lazy { findViewById<EditText>(R.id.main_input) }

    private val mDownloadButton: Button by lazy { findViewById<Button>(R.id.main_download_button) }

    private val mSendButton: Button by lazy { findViewById<Button>(R.id.main_send) }

    private val mWebClient: CacheWebClient by lazy { CacheWebClient() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initWebView()

        mSendButton.setOnClickListener {
            val url = mInputView.text.trim()
            if (!url.isBlank())
                mWebView.loadUrl(url.toString())
        }

        mDownloadButton.setOnClickListener {
            val url = mWebClient.getCacheVideo()?.trim()

            if (url != null && !url.isBlank()) {
                //创建下载任务,downloadUrl就是下载链接
                val request = DownloadManager.Request(Uri.parse(url))
                //指定下载路径和下载文件名
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "${mWebView.title}.mp4")
                //获取下载管理器
                val downloadManager = it.context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                //将下载任务加入下载队列，否则不会进行下载
                downloadManager.enqueue(request)
            }
        }
    }

    private fun initWebView() {
        @SuppressLint("SetJavaScriptEnabled")
        mWebView.settings.javaScriptEnabled = true
        mWebView.settings.mediaPlaybackRequiresUserGesture = false
        mWebView.settings.allowContentAccess = true
        mWebView.settings.pluginState = WebSettings.PluginState.ON
        mWebView.settings.domStorageEnabled = true
        mWebView.webChromeClient = WebChromeClient()
        mWebView.webViewClient = mWebClient
        mWebView.loadUrl(HAO_123)
    }
}