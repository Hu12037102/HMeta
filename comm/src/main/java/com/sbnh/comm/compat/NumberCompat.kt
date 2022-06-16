package com.sbnh.comm.compat

import com.sbnh.comm.Contract

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/15 16:38
 * 更新时间: 2022/6/15 16:38
 * 描述:
 */
object NumberCompat {
    @JvmStatic
    fun isPhoneNumber(phone: CharSequence?) =
        DataCompat.getTextLength(phone) == Contract.PHONE_NUMBER_LENGTH

    @JvmStatic
    fun isPayPassword(password: CharSequence?) =
        DataCompat.getTextLength(password) == Contract.PAY_PASSWORD_LENGTH

    @JvmStatic
    fun isMessageCode(messageCode: CharSequence?) =
        DataCompat.getTextLength(messageCode) == Contract.MESSAGE_CODE_LENGTH
}