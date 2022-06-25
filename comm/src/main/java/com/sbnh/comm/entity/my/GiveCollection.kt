package com.sbnh.comm.entity.my

data class GiveCollection(
    var id: String? = "",
    var resourceUrl: String? = "",
    var merchandiseName: String? = "",
    var nickname: String? = "",
    var createTime: Long? = 0,
    var tokenId: Long? = 0,
    var gainChannel: Int?,
)
