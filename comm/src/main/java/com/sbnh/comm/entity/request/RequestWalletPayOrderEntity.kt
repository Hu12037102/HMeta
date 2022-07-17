package com.sbnh.comm.entity.request

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/7/15 17:04
 * 更新时间: 2022/7/15 17:04
 * 描述:
 */
data class RequestWalletPayOrderEntity(
    val orderId: String,
    val payPassword: String
)