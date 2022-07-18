package com.sbnh.comm.entity.my

data class CollectionNumDetailsEntity(
    var id: String? = "",
    var createTime: Long? = 0,
    var tokenId: Long? = 0,
    var merchandiseId: String? = "",
    var price: Double?,
    var status: Int?
)
