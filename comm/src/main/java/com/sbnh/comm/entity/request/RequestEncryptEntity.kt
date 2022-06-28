package com.sbnh.comm.entity.request

import com.sbnh.comm.compat.DataCompat

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/28 16:28
 * 更新时间: 2022/6/28 16:28
 * 描述:
 */
data class RequestEncryptEntity(
    val timestamp: Long = System.currentTimeMillis(),
    val uuid: String = DataCompat.getOnlyId()
)
