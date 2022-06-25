package com.sbnh.comm.entity.my

import androidx.annotation.IntDef

// 全部
const val ALL = 0

// 赠送
const val GIVE = 4

// 领取
const val GET = 2

@IntDef(ALL, GIVE, GET)
@Retention(AnnotationRetention.SOURCE)
annotation class GiveType

data class GiveCollectionEntity(
    var id: String? = "",
    var resourceUrl: String? = "",
    var merchandiseName: String? = "",
    var nickname: String? = "",
    var createTime: Long? = 0,
    var tokenId: Long? = 0,
    var gainChannel: Int?,
)
