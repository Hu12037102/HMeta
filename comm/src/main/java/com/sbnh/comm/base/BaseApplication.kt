package com.sbnh.comm.base

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import kotlin.properties.Delegates

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/10 13:53
 * 更新时间: 2022/6/10 13:53
 * 描述:
 */
class BaseApplication : Application() {
    companion object {
         var mContext: Context by Delegates.notNull()
        @JvmStatic
        fun getContext(): Context {
            return mContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        mContext = this
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}