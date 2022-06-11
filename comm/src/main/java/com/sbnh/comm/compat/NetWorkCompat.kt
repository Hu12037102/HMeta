package com.sbnh.comm.compat

import android.content.Context
import android.net.ConnectivityManager
import com.sbnh.comm.base.BaseApplication

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/10 13:47
 * 更新时间: 2022/6/10 13:47
 * 描述:
 */
object NetWorkCompat {
    @JvmStatic
    private fun getNetWorkManger(): ConnectivityManager {
        return BaseApplication.getContext()
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    @JvmStatic
     fun isNetComment(): Boolean {
        return getNetWorkManger().isDefaultNetworkActive

    }

}