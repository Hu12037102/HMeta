package com.sbnh.comm.entity.pay

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/22 20:16
 * 更新时间: 2022/6/22 20:16
 * 描述:
 */
data class NumberQueryBankCardInfoEntity(
    var id: String? = "", var background: String? = "",
    var logo: String? = "", var name: String? = "",
    var preCardNo: String? = "", var createTime: Long? = 0,
    var isBindingBankCardBefore: Boolean = false
)