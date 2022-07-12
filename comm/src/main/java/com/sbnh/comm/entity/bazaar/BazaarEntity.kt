package com.sbnh.comm.entity.bazaar

import android.os.Parcelable
import com.sbnh.comm.entity.base.TabEntity
import kotlinx.parcelize.Parcelize

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/7/12 13:55
 * 更新时间: 2022/7/12 13:55
 * 描述:
 */
@Parcelize
data class BazaarEntity(
    val id: String?,
    val circulationNum: Int?,
    val createTime: Long?,
    val hasSoldOut: Boolean?,
    val header: String?,
    val merchandiseName: String?,
    val nickName: String?,
    val resourceUrl: String?,
    val totalQuantity: Int?,
    val uid: String?
):Parcelable
