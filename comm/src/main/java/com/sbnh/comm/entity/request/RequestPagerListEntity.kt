package com.sbnh.comm.entity.request

import com.sbnh.comm.Contract

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/20 14:34
 * 更新时间: 2022/6/20 14:34
 * 描述:
 */
data class RequestPagerListEntity(
    var pagerSize: Int = Contract.PAGE_LIST_SIZE,
    var time: Long = System.currentTimeMillis()
)