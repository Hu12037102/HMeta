package com.sbnh.comm.entity.request

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/22 21:19
 * 更新时间: 2022/6/22 21:19
 * 描述:
 */
data class RequestBindingBankCardBeforeEntity(
    var bankCode: String? = "",
    var cardNum: String? = "",
    var mobile: String? = ""

)