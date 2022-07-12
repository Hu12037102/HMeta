package com.sbnh.comm.entity.bazaar

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/7/12 11:44
 * 更新时间: 2022/7/12 11:44
 * 描述:
 */
data class BazaarTabEntity(
    val id: String?,
    val name: String?,
    val type: Int,
    var isSelector: Boolean = false
)
