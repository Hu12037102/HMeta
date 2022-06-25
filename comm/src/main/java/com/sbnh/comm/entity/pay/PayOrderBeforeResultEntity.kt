package com.sbnh.comm.entity.pay

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/24 17:44
 * 更新时间: 2022/6/24 17:44
 * 描述:
 */
data class PayOrderBeforeResultEntity(
    val cardType: String? = "",
    val hmac: String? = "",
    val merchantId: String? = "",
    val needKaptcha: String? = "",
    val paymentOrderId: String? = "",
    val requestId: String? = "",
    val scanCodeStr: String? = "",
    val status: String? = ""
)