package com.sbnh.comm.compat

import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/27 19:00
 * 更新时间: 2022/6/27 19:00
 * 描述:
 */
object IntentCompat {
    @JvmStatic
    @TargetApi(Build.VERSION_CODES.O)
    fun unknownApplicationInstallIntent() = Intent(
        Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES,
        Uri.parse("package:${DataCompat.getContext().packageName}")
    )
}