package com.sbnh.comm.other.smart

import com.scwang.smart.refresh.layout.SmartRefreshLayout

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/17 10:27
 * 更新时间: 2022/6/17 10:27
 * 描述:
 */
object SmartRefreshLayoutCompat {
    @JvmStatic
    fun initDefault(refreshLayout: SmartRefreshLayout?) {
        refreshLayout?.let {
            it.setEnableLoadMore(false)
            it.setEnableAutoLoadMore(true)
            it.setEnableOverScrollDrag(true)
            it.setEnableOverScrollBounce(true)
        }
    }
}