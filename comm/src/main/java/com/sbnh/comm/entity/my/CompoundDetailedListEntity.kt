package com.sbnh.comm.entity.my

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/7/7 13:57
 * 更新时间: 2022/7/7 13:57
 * 描述:
 */
data class CompoundDetailedListEntity(
    val desc: String? = "",
    val endTime: Long? = 0,
    val name: String? = "",
    val resource: String? = "",
    val startTime: Long? = 0,
    val synthesisConfigId: String? = "",
    val id: String? = ""
)