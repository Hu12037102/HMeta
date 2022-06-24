package com.sbnh.comm.compat

import android.annotation.SuppressLint
import android.webkit.WebSettings
import android.webkit.WebView

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/19 21:56
 * 更新时间: 2022/6/19 21:56
 * 描述:
 */
object WebViewCompat {
    //内链
    const val SKIP_TYPE_IN = 1
    //外链
    const val SKIP_TYPE_OUT = 2

    @SuppressLint("SetJavaScriptEnabled")
    @JvmStatic
    fun init(webView: WebView) {
        val webSetting = webView.settings
        webSetting.javaScriptEnabled = true
        webSetting.javaScriptCanOpenWindowsAutomatically = true
        webSetting.loadWithOverviewMode = true
        webSetting.setSupportZoom(false)
        webSetting.builtInZoomControls = true
        webSetting.displayZoomControls = true
        webSetting.cacheMode = if (NetWorkCompat.isNetComment()) {
            WebSettings.LOAD_NO_CACHE
        } else {
            WebSettings.LOAD_CACHE_ELSE_NETWORK
        }
        webSetting.allowFileAccess = true
        webSetting.loadsImagesAutomatically = true
        webSetting.defaultTextEncodingName = Charsets.UTF_8.name()
        webSetting.domStorageEnabled = true
        webSetting.databaseEnabled = true
        webSetting.setGeolocationEnabled(true)
        webSetting.useWideViewPort = true
        webSetting.mediaPlaybackRequiresUserGesture = true
        webSetting.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
    }

}