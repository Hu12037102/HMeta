package com.sbnh.comm.entity.request

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/7/13 13:37
 * 更新时间: 2022/7/13 13:37
 * 描述:
 */
data class RequestBazaarDataEntity(
    val pageNum: Int,
    val pageSize: Int,
    val priceSort: Boolean,
    val secondaryCategoryId: String,
    val time: Long,
    val type: Int
)