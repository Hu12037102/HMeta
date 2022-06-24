package com.sbnh.comm.http

import java.net.HttpURLConnection
import java.util.*

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/18 13:51
 * 更新时间: 2022/6/18 13:51
 * 描述:
 */
class IApiService {
    object Path {
        const val USER_LOGIN = "/healer_nft/user/login"
        const val GAIN_MESSAGE_CODE = "/healer_nft/user/validate_code"
        const val REGISTER = "/healer_nft/user/registered"
        const val REAL_NAME_AUTHENTICATION = "/healer_nft/user/realName"
        const val EXIT_LOGIN = "/healer_nft/user/login_out"
        const val HOME_PAGER_LIST = "/healer_nft/Merchandise/pageList"
        const val COLLECTION_DETAILS = "/healer_nft/Merchandise/home_page_details/{id}"
        const val COMMIT_ORDER = "/payment/thirdPayment/submitOrder"
        const val QUERY_ORDER_DETAILS = "/payment/thirdPayment/getOrder/{id}"
        const val QUERY_MY_ORDER_LIST = "/payment/thirdPayment/queryUserOrder"
        const val SET_PAYMENT_PASSWORD = "/healer_nft/user/userPayPassword"
        const val QUERY_BANK_CARD_INFO = "/payment/thirdPayment/getBank"
        const val BINDING_BANK_CARD_BEFORE = "/payment/thirdPayment/bindCard"
        const val BINDING_BANK_CARD_AFTER = "/payment/thirdPayment/bindCardConfirm"
        const val QUERY_BANK_CARD_LIST = "payment/thirdPayment/queryBankCardBindRec"
        const val MY_COLLECTION_LIST = "/healer_nft/user/myCollectionNew"
        const val COLLECTION_NUM_DETAILS = "/healer_nft/user/collectionNum"
    }

    object HttpCode {
        const val SUCCEED = HttpURLConnection.HTTP_OK
        const val CLIENT_ERROR = HttpURLConnection.HTTP_BAD_REQUEST
        const val CLIENT_NOT_LOGIN = HttpURLConnection.HTTP_UNAUTHORIZED
        const val SERVICE_ERROR = HttpURLConnection.HTTP_INTERNAL_ERROR
    }

    object Key {
        const val CONTENT_TYPE = "Content-Type";
        const val CHARSET = "charset";
        const val ACCESS_TOKEN = "accessToken";
        const val TS = "ts";
        const val UUID = "uuid";
        const val SID = "sid";
        const val ID = "id"
    }
}