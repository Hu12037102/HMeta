package com.sbnh.comm.entity.request

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/24 17:41
 * 更新时间: 2022/6/24 17:41
 * 描述:
 */
data class RequestPayOrderBeforeEntity(
    val bindCardId: String?,
    val merchandiseId: String,
    val orderId: String
)
