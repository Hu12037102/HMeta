package com.sbnh.comm.config

import android.os.Build
import com.sbnh.comm.BuildConfig

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/10 12:02
 * 更新时间: 2022/6/10 12:02
 * 描述:
 */
object AppConfig {
    @JvmStatic
    fun isShowLog(): Boolean {
        return BuildConfig.DEBUG
    }
}