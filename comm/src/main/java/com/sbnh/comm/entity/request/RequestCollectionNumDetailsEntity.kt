package com.sbnh.comm.entity.request

import com.sbnh.comm.Contract

data class RequestCollectionNumDetailsEntity(
    val id: String,
    val merchandiseId: String,
    var pageNum: Int = Contract.PAGE_NUM,
    var pageSize: Int = Contract.PAGE_SIZE,
    var time: Long = System.currentTimeMillis()
)
