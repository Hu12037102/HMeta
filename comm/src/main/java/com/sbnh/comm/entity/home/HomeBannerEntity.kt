package com.sbnh.comm.entity.home

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/24 10:24
 * 更新时间: 2022/6/24 10:24
 * 描述:
 */
//1、内链
//2、外链

data class HomeBannerEntity(
    var desc: String? = "",
    var resource: String? = "",
    var skipType: Int? = 0,
    var skipUrl: String? = ""
)
