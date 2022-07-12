package com.sbnh.comm.entity.request

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/7/12 14:05
 * 更新时间: 2022/7/12 14:05
 * 描述:
 */
data class RequestBazaarEntity(
    val merchandiseTypeId: String,
    val pageNum: Int,
    val pageSize: Int,
    val time: Long
)