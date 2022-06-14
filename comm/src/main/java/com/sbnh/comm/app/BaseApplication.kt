package com.sbnh.comm.app

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.multidex.MultiDex
import com.alibaba.android.arouter.launcher.ARouter
import com.sbnh.comm.config.AppConfig
import com.sbnh.comm.manger.ActivityCompatManger
import kotlin.properties.Delegates

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/10 13:53
 * 更新时间: 2022/6/10 13:53
 * 描述:
 */
class BaseApplication : Application() {
    companion object {
        private var mContext: Context by Delegates.notNull()

        @JvmStatic
        fun getContext(): Context {
            return mContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        init()
        this.registerActivityLifecycleCallbacks(mRegisterActivityCallback)
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

    }

    private fun initARouter() {
        if (AppConfig.isDebug()) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        this.unregisterActivityLifecycleCallbacks(mRegisterActivityCallback)
    }
}