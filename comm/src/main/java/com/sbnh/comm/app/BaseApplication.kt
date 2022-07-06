package com.sbnh.comm.app

import android.app.Activity
import android.app.Application
import android.app.usage.NetworkStatsManager
import android.content.Context
import android.net.*
import android.os.Bundle
import androidx.core.net.ConnectivityManagerCompat
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.alibaba.android.arouter.launcher.ARouter
import com.sbnh.comm.BuildConfig
import com.sbnh.comm.Contract
import com.sbnh.comm.compat.NetWorkCompat
import com.sbnh.comm.config.AppConfig
import com.sbnh.comm.manger.ActivityCompatManger
import com.sbnh.comm.utils.LogUtils
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.MaterialHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.tencent.bugly.crashreport.CrashReport
import com.tencent.bugly.crashreport.CrashReport.UserStrategy
import kotlin.properties.Delegates

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/10 13:53
 * 更新时间: 2022/6/10 13:53
 * 描述:
 */
class BaseApplication : MultiDexApplication() {
    companion object {
        private var mContext: Context by Delegates.notNull()
        private var isNetComment: Boolean = false

        @JvmStatic
        fun getContext(): Context {
            return mContext
        }

        @JvmStatic
        fun isNetComment() = isNetComment

        init {
            SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, _ ->
                MaterialHeader(
                    context
                )
            }
            SmartRefreshLayout.setDefaultRefreshFooterCreator { context, _ ->
                ClassicsFooter(
                    context
                )
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        init()
        this.registerActivityLifecycleCallbacks(mRegisterActivityCallback)
        initNetCallback()
    }

    private fun initNetCallback() {
        NetWorkCompat.getNetWorkManger().registerNetworkCallback(NetworkRequest.Builder().build(),
            object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    isNetComment = true
                    LogUtils.w("BaseApplication--", "onAvailable--${network}")
                }

                override fun onBlockedStatusChanged(network: Network, blocked: Boolean) {
                    super.onBlockedStatusChanged(network, blocked)
                    LogUtils.w("BaseApplication--", "onBlockedStatusChanged--${network}--$blocked")
                }

                override fun onCapabilitiesChanged(
                    network: Network,
                    networkCapabilities: NetworkCapabilities
                ) {
                    super.onCapabilitiesChanged(network, networkCapabilities)
                    LogUtils.w(
                        "BaseApplication--",
                        "onCapabilitiesChanged--${network}--$networkCapabilities"
                    )
                }

                override fun onLinkPropertiesChanged(
                    network: Network,
                    linkProperties: LinkProperties
                ) {
                    super.onLinkPropertiesChanged(network, linkProperties)
                    LogUtils.w(
                        "BaseApplication--",
                        "onLinkPropertiesChanged--${network}--$linkProperties"
                    )
                }

                override fun onLosing(network: Network, maxMsToLive: Int) {
                    super.onLosing(network, maxMsToLive)
                    LogUtils.w("BaseApplication--", "onLosing--${network}--$maxMsToLive")
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    isNetComment = false
                    LogUtils.w("BaseApplication--", "onLost--${network}--")
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    isNetComment= false
                    LogUtils.w("BaseApplication--", "onUnavailable--")
                }
            })

    }

    private val mRegisterActivityCallback = object : ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            ActivityCompatManger.get().add(activity)
        }

        override fun onActivityStarted(activity: Activity) {
        }

        override fun onActivityResumed(activity: Activity) {
        }

        override fun onActivityPaused(activity: Activity) {
        }

        override fun onActivityStopped(activity: Activity) {
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        }

        override fun onActivityDestroyed(activity: Activity) {
            ActivityCompatManger.get().remove(activity)
        }

    }

    private fun init() {
        mContext = this
        initARouter()
        initBugly()
    }

    private fun initARouter() {
        if (AppConfig.isDebug()) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)
    }

    private fun initBugly() {
        if (!AppConfig.isDebug()) {
            val strategy = UserStrategy(this)
            strategy.appChannel = if (AppConfig.isDebug()) Contract.DEBUG else Contract.RELEASE
            CrashReport.initCrashReport(this, BuildConfig.BUGLY_ID, false, strategy)
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        //  MultiDex.install(this)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        this.unregisterActivityLifecycleCallbacks(mRegisterActivityCallback)
    }
}