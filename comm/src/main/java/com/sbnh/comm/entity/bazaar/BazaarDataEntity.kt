package com.sbnh.comm.entity.bazaar

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/7/13 13:46
 * 更新时间: 2022/7/13 13:46
 * 描述:
 */
data class BazaarDataEntity(
    val id: String?,
    val marketSecondaryCategoryId: String?,
    val price: Double,
    val status: Int,
    val tokenId: String,
    val updateTime: Long,
    val userCollectionId: String
)