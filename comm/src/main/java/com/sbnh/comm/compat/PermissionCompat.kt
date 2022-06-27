package com.sbnh.comm.compat

import android.content.IntentFilter
import android.os.Build

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/27 19:10
 * 更新时间: 2022/6/27 19:10
 * 描述:
 */
object PermissionCompat {
    @JvmStatic
    fun hasCanRequestPackageInstalls() =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) DataCompat.getContext().packageManager.canRequestPackageInstalls() else true

}