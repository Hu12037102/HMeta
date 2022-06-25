package com.sbnh.comm.entity.request

import com.sbnh.comm.Contract

data class RequestGiveCollectionListEntity(
    var pageNum: Int = Contract.PAGE_NUM,
    var pageSize: Int = Contract.PAGE_SIZE,
    var type: Int = 0,
)
