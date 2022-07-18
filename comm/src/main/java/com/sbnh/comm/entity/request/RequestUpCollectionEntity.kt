package com.sbnh.comm.entity.request

import com.sbnh.comm.Contract

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/7/18 12:16
 * 更新时间: 2022/7/18 12:16
 * 描述:
 */
data class RequestUpCollectionEntity(
    var price: Double = Contract.NOT_MONEY,
    var userCollectionId: String? = ""
)
