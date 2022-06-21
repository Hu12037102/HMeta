package com.sbnh.comm.entity.order

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/21 20:15
 * 更新时间: 2022/6/21 20:15
 * 描述:
 */
data class OrderTabEntity(
    @OrderStatus val id: Int? = null,
    val name: String = "",
    var isSelector: Boolean = false
)
