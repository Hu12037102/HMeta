package com.sbnh.comm.entity.order

import com.sbnh.comm.Contract

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/22 12:53
 * 更新时间: 2022/6/22 12:53
 * 描述:
 */
data class RequestOrderListEntity(
    var pageNum: Int = Contract.PAGE_NUM,
    var pageSize: Int = Contract.PAGE_SIZE,
    var status: Int? = null
)
