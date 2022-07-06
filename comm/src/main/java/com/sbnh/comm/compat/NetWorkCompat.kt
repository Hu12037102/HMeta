package com.sbnh.comm.compat

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import androidx.core.net.ConnectivityManagerCompat
import com.sbnh.comm.app.BaseApplication

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/10 13:47
 * 更新时间: 2022/6/10 13:47
 * 描述:
 */
object NetWorkCompat {
    @JvmStatic
    fun getNetWorkManger(): ConnectivityManager {
        return DataCompat.getContext()
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    /* @JvmStatic
     fun isNetComment(): Boolean = getNetWorkManger().activeNetworkInfo?.isConnected ?: false*/

    @JvmStatic
    fun isNetComment(): Boolean = BaseApplication.isNetComment()
}