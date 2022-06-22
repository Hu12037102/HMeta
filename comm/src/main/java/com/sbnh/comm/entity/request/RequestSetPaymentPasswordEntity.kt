package com.sbnh.comm.entity.request

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/22 19:39
 * 更新时间: 2022/6/22 19:39
 * 描述:
 */
data class RequestSetPaymentPasswordEntity(
    val payPassword: String = "",
    val verificationCode: String = ""
)
