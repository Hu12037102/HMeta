package com.sbnh.comm.entity.request

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/7/18 17:06
 * 更新时间: 2022/7/18 17:06
 * 描述:
 */
data class RequestPagerTypeEntity(
    val pageNum: Int,
    val pageSize: Int,
    val time: Long,
    val type: Int
)
