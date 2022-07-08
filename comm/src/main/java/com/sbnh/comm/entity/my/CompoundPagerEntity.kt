package com.sbnh.comm.entity.my

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/7/7 15:10
 * 更新时间: 2022/7/7 15:10
 * 描述:
 */
data class CompoundPagerEntity(
    val afterSynthesis: List<Details>?,
    val synthesisMerchandiseBO: List<Details>?
) {


    data class Details(
        val count: Int? = 0,
        val merchandiseId: String? = "",
        val merchandiseName: String? = "",
        val resourceUrl: String? = "",
        var availableCount: Int? = 0
    )
}
