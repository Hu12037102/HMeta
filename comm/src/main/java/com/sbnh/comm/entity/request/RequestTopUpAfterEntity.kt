package com.sbnh.comm.entity.request

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/7/14 10:15
 * 更新时间: 2022/7/14 10:15
 * 描述:
 */
data class RequestTopUpAfterEntity(
    val kaptchaCode: String,
    val paymentOrderId: String,
    val requestId: String
)
