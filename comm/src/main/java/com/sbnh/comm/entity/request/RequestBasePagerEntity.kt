package com.sbnh.comm.entity.request

import com.sbnh.comm.Contract

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/23 11:08
 * 更新时间: 2022/6/23 11:08
 * 描述:
 */
data class RequestBasePagerEntity(val page: Int = Contract.PAGE_NUM, val size: Int = Contract.PAGE_SIZE)
