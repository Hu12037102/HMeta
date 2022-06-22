package com.sbnh.comm.entity.request

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/19 14:13
 * 更新时间: 2022/6/19 14:13
 * 描述:
 */
data class RequestRealNameEmpty(
    val name: String = "", val card: String = "",
    val ticket: String ?= "", val randstr: String ?= ""
)
