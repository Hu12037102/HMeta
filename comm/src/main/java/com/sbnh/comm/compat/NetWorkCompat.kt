package com.sbnh.comm.compat

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.core.net.ConnectivityManagerCompat
import com.sbnh.comm.app.BaseApplication
import com.sbnh.comm.manger.NetWorkManger

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

     @JvmStatic
     fun isNetComment(): Boolean = getNetWorkManger().activeNetworkInfo?.isConnected ?: false

     /* @JvmStatic
      fun isNetComment(): Boolean = NetWorkManger.get().isNetComment()*/
  /*  @JvmStatic
    fun isNetComment(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = getNetWorkManger().activeNetwork
            val capabilities = getNetWorkManger().getNetworkCapabilities(network)
            capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED) ?: false
        } else {
            val netWorkInfo = getNetWorkManger().activeNetworkInfo
            netWorkInfo?.isAvailable ?: false
        }
    }*/

}