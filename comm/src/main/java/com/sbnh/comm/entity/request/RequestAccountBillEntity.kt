package com.sbnh.comm.entity.request

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/7/15 11:16
 * 更新时间: 2022/7/15 11:16
 * 描述:
 */
data class RequestAccountBillEntity(
    val pageNum: Int,
    val pageSize: Int,
    val targetType: Int,
    val time: Long
)