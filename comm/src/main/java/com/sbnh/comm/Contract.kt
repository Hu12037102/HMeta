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
    const val DEBUG = "debug"
    const val RELEASE = "release"
    const val MIN_TOP_UP_MONEY = 10
    const val DEFAULT_STRING_VALUE = ""
    const val MIN_WITHDRAW_MONEY = 30
    const val NOT_MONEY = 0.00
    const val MIN_SET_TRANSACTION_MONEY = 1
    const val MAX_SET_TRANSACTION_MONEY = 99999
    const val MONEY_KEEP_DIGIT = 2
    const val MIN_INT_VALUE=0

    object DP {
        const val VALUE_8F = 8f
        const val VALUE_50F = 50f
        const val VALUE_12F = 12f
        const val VALUE_2F = 2f
        const val COLLECTION_IMAGE_SMALL_SIZE = 86

    }

    object CollectionStatus {
        //0寄售 1售出 2预定中 3下架
        const val STATUS_SALE = 0
        const val STATUS_SELL = 1
        const val STATUS_RESERVE = 2
        const val STATUS_SOLD_OUT = 3
    }


    object PutOrderType {

        //抢购(官方抢购购买)
        const val OFFICIAL = 1

        //市场购买
        const val BAZAAR_BUY = 2

        //转赠
        const val GIVE = 3
    }

    object PayWay {
        //未选中支付方式
        const val UNKNOWN = 0

        //银行卡
        const val BANK_CARD = 1

        //钱包余额
        const val WALLET_BALANCE = 2
    }

    object MyCollectionStatus {
        //0正常 1转增出去 2锁定（预定中） 3寄售 4卖出
        const val NORMAL: Int = 0
        const val GIVE: Int = 1
        const val LOCK: Int = 2
        const val SALE: Int = 3
        const val OUT: Int = 4
    }
}