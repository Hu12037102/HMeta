package com.sbnh.comm.entity.my

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/7/15 10:28
 * 更新时间: 2022/7/15 10:28
 * 描述:
 */
data class AccountBillEntity(
    val amount: Double?,
    val createTime: Long?,
    val desc: String?,
    val id: String?,
    val resource: String?,
    val targetStatus: Int?,
    val targetType: Int?
)