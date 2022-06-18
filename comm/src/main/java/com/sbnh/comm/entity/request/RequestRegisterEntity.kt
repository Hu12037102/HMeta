package com.sbnh.comm.entity.request

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/18 17:13
 * 更新时间: 2022/6/18 17:13
 * 描述:
 */
data class RequestRegisterEntity(
    var mobile: String = "",
    var verificationCode: String = "",
    var fillInviteCode: String = ""

)
