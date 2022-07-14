package com.sbnh.comm.entity.request

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/7/14 9:59
 * 更新时间: 2022/7/14 9:59
 * 描述:
 */
data class RequestTopUpBeforeEntity(
    val amount: Double,
    val bindCardId: String?
)
