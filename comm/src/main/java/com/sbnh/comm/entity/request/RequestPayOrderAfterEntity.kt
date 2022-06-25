package com.sbnh.comm.entity.request

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/25 13:55
 * 更新时间: 2022/6/25 13:55
 * 描述:
 */
data class RequestPayOrderAfterEntity(
    val kaptchaCode: String,
    val paymentOrderId: String,
    val requestId: String
)
