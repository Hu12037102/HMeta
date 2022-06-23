package com.sbnh.comm.entity.pay

import androidx.annotation.DrawableRes
import androidx.annotation.IntDef

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/23 17:55
 * 更新时间: 2022/6/23 17:55
 * 描述:
 */
const val SOFT_TYPE_NUMBER = 1
const val SOFT_TYPE_DELETE = 2
const val SOFT_TYPE_UNKNOWN = 0

@IntDef(SOFT_TYPE_NUMBER, SOFT_TYPE_DELETE, SOFT_TYPE_UNKNOWN)
@Retention(AnnotationRetention.SOURCE)
annotation class SoftType

data class PaymentPasswordNumberEntity(
    @SoftType val type: Int = SOFT_TYPE_NUMBER,
    val text: String? = null,
    val value: Int? = null,
    @DrawableRes val resDrawable: Int = 0,
)
