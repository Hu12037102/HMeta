package com.sbnh.comm.base.activity.web

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.text.TextUtils
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.alibaba.android.arouter.facade.annotation.Route
import com.sbnh.comm.base.viewmodel.WebContentViewModel
import com.sbnh.comm.databinding.ActivityWebContentBinding
import com.sbnh.comm.other.arouter.ARouterConfig

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/19 22:22
 * 更新时间: 2022/6/19 22:22
 * 描述:
 */
@Route(path = ARouterConfig.Path.Comm.ACTIVITY_WEB_CONTENT)
class WebContentActivity : BaseWebActivity<ActivityWebContentBinding, WebContentViewModel>() {
    override fun getViewBinding(): ActivityWebContentBinding =
        ActivityWebContentBinding.inflate(layoutInflater)

    override fun getViewModelClass(): Class<WebContentViewModel> = WebContentViewModel::class.java
    override fun getWebView(): WebView = mViewBinding.webView
    override fun initView() {
    }

    override fun initData() {
        loadUrl("http://www.baidu.com")
    }


    override fun initEvent() {
        mWebView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    mViewBinding.pbSchedule.setProgress(newProgress, true)
                } else {
                    mViewBinding.pbSchedule.progress = newProgress
                }
                if (newProgress >= mViewBinding.pbSchedule.max) {
                    mViewBinding.pbSchedule.visibility = View.GONE
                } else {
                    mViewBinding.pbSchedule.visibility = View.VISIBLE
                }
            }

            override fun onReceivedTitle(view: WebView?, title: String?) {
                super.onReceivedTitle(view, title)

            }
        }
        mWebView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                request?.url?.let {
                    try {
                        if (it.scheme?.startsWith("Http", true) == true) {
                            loadUrl(it.toString())
                        } else {
                            startActivity(Intent(Intent.ACTION_VIEW, it))
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }


                }

                return super.shouldOverrideUrlLoading(view, request)
            }

        }
    }


}