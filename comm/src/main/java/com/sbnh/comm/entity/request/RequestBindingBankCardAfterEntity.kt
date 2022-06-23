package com.sbnh.comm.entity.request

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/23 10:42
 * 更新时间: 2022/6/23 10:42
 * 描述:
 */
data class RequestBindingBankCardAfterEntity(
    var bindCardId: String = "",
    var kaptchaCode: String = ""
)
