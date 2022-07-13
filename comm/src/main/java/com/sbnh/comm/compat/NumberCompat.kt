package com.sbnh.comm.compat

import com.sbnh.comm.Contract
import com.sbnh.comm.utils.LogUtils

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/15 16:38
 * 更新时间: 2022/6/15 16:38
 * 描述:
 */
object NumberCompat {
    @JvmStatic
    fun isPhoneNumber(phone: CharSequence?): Boolean {
        return DataCompat.getTextLength(phone, true) == Contract.PHONE_NUMBER_LENGTH
    }


    @JvmStatic
    fun isPayPassword(password: CharSequence?) =
        DataCompat.getTextLength(password) == Contract.PAY_PASSWORD_LENGTH

    @JvmStatic
    fun isMessageCode(messageCode: CharSequence?) =
        DataCompat.getTextLength(messageCode) == Contract.MESSAGE_CODE_LENGTH

    @JvmStatic
    fun isBankCardNumber(number: CharSequence?) =
        DataCompat.getTextLength(number, true) >= Contract.BANK_CARD_NUMBER_MIN
                &&
                DataCompat.getTextLength(number, true) <= Contract.BANK_CARD_NUMBER_MAX

    @JvmStatic
    fun isIdCard(idCard: CharSequence?) =
        DataCompat.getTextLength(idCard, true) >= Contract.ID_CARD_NUMBER_MIN
                &&
                DataCompat.getTextLength(idCard, true) <= Contract.ID_CARD_NUMBER_MAX

    @JvmStatic
    fun encryptPhoneNumber(phoneNumber: String?): String {
        var result = ""
        phoneNumber?.let {
            for (i in phoneNumber.indices) {
                val char = phoneNumber[i]
                result += if (i in 3..6) {
                    "*"
                } else {
                    char
                }
            }
        }
        return result
    }

    @JvmStatic
    fun numberSub(number: Int?, base: Int?, defaultNumber: Int = 0): Int {
        if (number == null || base == null) {
            return defaultNumber
        }
        val result = number - base
        return if (result >= defaultNumber) result else defaultNumber
    }

    @JvmStatic
    fun intValue(number: Int?, defaultNumber: Int? = 0) = number ?: defaultNumber

    @JvmStatic
    fun checkInt(number: Int?, defaultNumber: Int = 0) = number ?: defaultNumber
}