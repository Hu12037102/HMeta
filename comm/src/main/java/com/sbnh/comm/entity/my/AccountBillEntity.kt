package com.sbnh.comm.entity.my

import androidx.annotation.IntDef

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/7/15 10:28
 * 更新时间: 2022/7/15 10:28
 * 描述:
 */

const val ING = 1
const val FAIL = 2
const val SUCCEED = 3

@IntDef( ING, FAIL, SUCCEED)
@Retention(AnnotationRetention.SOURCE)
annotation class TargetStatus

data class AccountBillEntity(
    val amount: Double?,
    val createTime: Long?,
    val desc: String?,
    val id: String?,
    val resource: String?,
    @TargetStatus
    val targetStatus: Int?,
    val targetType: Int?
)