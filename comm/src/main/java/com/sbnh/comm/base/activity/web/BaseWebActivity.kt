package com.sbnh.comm.base.activity.web

import android.view.ViewGroup
import android.webkit.WebView
import androidx.viewbinding.ViewBinding
import com.sbnh.comm.base.activity.BaseCompatActivity
import com.sbnh.comm.base.viewmodel.BaseViewModel
import com.sbnh.comm.compat.WebViewCompat
import com.sbnh.comm.utils.LogUtils

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/19 21:54
 * 更新时间: 2022/6/19 21:54
 * 描述:
 */
abstract class BaseWebActivity<VB : ViewBinding, VM : BaseViewModel> :
    BaseCompatActivity<VB, VM>() {
    protected val mWebView: WebView by lazy {
        getWebView()
    }

    protected abstract fun getWebView(): WebView

    protected fun loadUrl(url: String?) {
        if (url == null) {
            return
        }
        WebViewCompat.init(mWebView)
        LogUtils.w("loadUrl--",url)
        mWebView.loadUrl(url)
    }

    override fun onStart() {
        super.onStart()
        mWebView.resumeTimers()
    }

    override fun onResume() {
        super.onResume()
        mWebView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mWebView.onPause()
    }

    override fun onStop() {
        super.onStop()
        mWebView.pauseTimers()
    }

    override fun onDestroy() {
        val parent = mWebView.parent
        if (parent is ViewGroup) {
            parent.removeView(mWebView)
            mWebView.destroy()
        }
        super.onDestroy()

    }

    override fun onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack()
        } else {
            super.onBackPressed()
        }
    }

}


