package com.sbnh.comm.entity.my

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MyCollectionEntity(
    var id: String? = "",
    var count: Int? = 0,
    var header: String? = "",
    var merchandiseId: String? = "",
    var merchandiseName: String? = "",
    var nickname: String? = "",
    var resourceUrl: String? = "",
    var createTime: Long?,
    var price: Double?,
    var status: Int?,
    var tokenId: Long?
) : Parcelable
