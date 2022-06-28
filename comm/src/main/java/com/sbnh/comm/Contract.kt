package com.sbnh.comm

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/15 11:34
 * 更新时间: 2022/6/15 11:34
 * 描述:
 */
object Contract {
    const val MESSAGE_CODE_DOWN_TIME_LENGTH = 60L
    const val PHONE_NUMBER_LENGTH = 11
    const val PAY_PASSWORD_LENGTH = 6
    const val MESSAGE_CODE_LENGTH = 6
    const val BANK_CARD_NUMBER_MIN = 16
    const val BANK_CARD_NUMBER_MAX = 19
    const val ID_CARD_NUMBER_MIN = 15
    const val ID_CARD_NUMBER_MAX = 18
    const val PAGE_SIZE = 20
    const val PAGE_NUM = 1
    const val DEFAULT_TIME_FORMAT = "yyyy-MM-dd HH:mm"
    const val UNKNOWN_INT_VALUE = -1
    const val UNKNOWN_LONG_VALUE = -1L
    const val DEBUG="debug"
    const val RELEASE="release"

    object DP {
        const val VALUE_8F = 8f
        const val VALUE_50F = 50f
        const val COLLECTION_IMAGE_SMALL_SIZE = 86
    }

    object Id{
        const val BUGLY_APP_ID="2f9ae5bbf2"
    }
}