package com.sbnh.comm.entity.request

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/7/14 16:16
 * 更新时间: 2022/7/14 16:16
 * 描述:
 */
data class RequestWithdrawBankCardEntity(
    val bindCardId: String,
    val payPassword: String,
    val withdrawAmount: Double
)
