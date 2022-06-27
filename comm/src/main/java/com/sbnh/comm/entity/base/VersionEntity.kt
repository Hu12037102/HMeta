package com.sbnh.comm.entity.base

import android.os.Parcelable
import androidx.annotation.IntDef
import kotlinx.parcelize.Parcelize

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/25 16:17
 * 更新时间: 2022/6/25 16:17
 * 描述:
 */
const val VERSION_NOT_UPDATE = 0
const val VERSION_MUST_UPDATE = 1
const val VERSION_SELECTOR_UPDATE = 2

@IntDef(VERSION_NOT_UPDATE, VERSION_MUST_UPDATE, VERSION_SELECTOR_UPDATE)
@Retention(AnnotationRetention.SOURCE)
annotation class VersionStatus

@Parcelize
data class VersionEntity(
    val changes: List<String>,
    val code: Int?,
    val date: String?,
    val downUrls: String?,
    @VersionStatus
    val status: Int?,//0不用更新，1强更，2选择性更新
    val version: String?
) : Parcelable
