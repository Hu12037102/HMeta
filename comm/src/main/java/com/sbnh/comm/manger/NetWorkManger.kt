package com.sbnh.comm.manger

import android.net.*
import com.sbnh.comm.compat.NetWorkCompat
import com.sbnh.comm.utils.LogUtils

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/7/8 14:13
 * 更新时间: 2022/7/8 14:13
 * 描述:
 */
class NetWorkManger private constructor() : ConnectivityManager.NetworkCallback() {
    companion object {
        private val mInstance: NetWorkManger by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { NetWorkManger() }

        @JvmStatic
        fun get(): NetWorkManger = mInstance
    }

    private var isNetComment: Boolean = false

    fun isNetComment() = isNetComment
    fun init() {
        NetWorkCompat.getNetWorkManger()
            .registerNetworkCallback(
                NetworkRequest.Builder().addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                    .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR).build(), this
            )
    }
    //网络连接成功
    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        LogUtils.w("NetWorkManger--", "onAvailable--${network}")
        isNetComment = true
    }
    //当访问的网络被阻塞或者解除阻塞时调用
    override fun onBlockedStatusChanged(network: Network, blocked: Boolean) {
        super.onBlockedStatusChanged(network, blocked)
        LogUtils.w("NetWorkManger--", "onBlockedStatusChanged--${network}--$blocked")
    }
    //当网络状态修改（网络依然可用）时调用
    override fun onCapabilitiesChanged(
        network: Network,
        networkCapabilities: NetworkCapabilities
    ) {
        super.onCapabilitiesChanged(network, networkCapabilities)
        LogUtils.w(
            "NetWorkManger--",
            "onCapabilitiesChanged--${network}--$networkCapabilities"
        )
    }
    //当网络连接属性发生变化时调用
    override fun onLinkPropertiesChanged(
        network: Network,
        linkProperties: LinkProperties
    ) {
        super.onLinkPropertiesChanged(network, linkProperties)
        LogUtils.w(
            "NetWorkManger--",
            "onLinkPropertiesChanged--${network}--$linkProperties"
        )
    }
    //网络正在断开连接
    override fun onLosing(network: Network, maxMsToLive: Int) {
        super.onLosing(network, maxMsToLive)
        LogUtils.w("NetWorkManger--", "onLosing--${network}--$maxMsToLive")
    }
    //网络已断开连接
    override fun onLost(network: Network) {
        super.onLost(network)
        isNetComment = false
        LogUtils.w("NetWorkManger--", "onLost--${network}--")
    }
    // //无网络
    override fun onUnavailable() {
        super.onUnavailable()
        isNetComment = false
        LogUtils.w("NetWorkManger--", "onUnavailable--")
    }
}