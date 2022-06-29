package com.sbnh.comm.http

import com.sbnh.comm.BuildConfig
import java.net.HttpURLConnection

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
        const val UNBIND_BANK_CARD = "/payment/thirdPayment/unBindCard"
        const val HOME_BANNER = "/healer_nft/notice"
        const val PAY_ORDER_BEFORE = "/payment/thirdPayment/pay"
        const val PAY_COLLECTION_AFTER = "/payment/thirdPayment/payConfirm"
        const val CANCEL_ORDER = "/payment/thirdPayment/cancelOrder"
        const val MY_COLLECTION_LIST = "/healer_nft/user/myCollectionNew"
        const val COLLECTION_NUM_DETAILS = "/healer_nft/user/collectionNum"
        const val GIVE_COLLECTION = "/healer_nft/Presented/presentedMerchandise"
        const val GIVE_COLLECTION_LIST = "/healer_nft/Presented/presentedRecord"
        const val LOAD_APP_VERSION = "/healer_nft/Version/getVersion/{code}"
        const val KNAPSACK_COLLECTION_DETAILS =
            "/healer_nft/Merchandise/knapsack_details/{cid}/{merchandiseId}"
    }

    object HttpCode {
        const val SUCCEED = HttpURLConnection.HTTP_OK
        const val CLIENT_ERROR = HttpURLConnection.HTTP_BAD_REQUEST
        const val CLIENT_NOT_LOGIN = HttpURLConnection.HTTP_UNAUTHORIZED
        const val SERVICE_ERROR = HttpURLConnection.HTTP_INTERNAL_ERROR
        const val NET_OFF_LINE=HttpURLConnection.HTTP_GATEWAY_TIMEOUT
    }

    object Key {
        const val CONTENT_TYPE = "Content-Type";
        const val CHARSET = "charset";
        const val ACCESS_TOKEN = "accessToken";
        const val TS = "ts";
        const val UUID = "uuid";
        const val SID = "sid";
        const val ID = "id"
        const val VERSION = "version"
        const val CODE = "code"
        const val OS = "os"
        const val CID = "cid"
        const val MERCHANDISE_ID = "merchandiseId"
        const val NONCE = "nonce"
        const val SIGNATURE = "signature"
    }

    object Value {
        const val ANDROID = "Android"
    }

    object H5 {
        //邀请好友
        const val INVITE_FRIEND =
            "${BuildConfig.BASE_WEB_URL}/healer_nft_web/nft_page/#/sub/invite_page"

        //隐私政策
        const val PRIVACY_POLICY =
            "${BuildConfig.BASE_WEB_URL}/healer_nft_web/nft_privacy/index.html"

        //用户协议
        const val USER_AGREEMENT =
            "${BuildConfig.BASE_WEB_URL}/healer_nft_web/nft_user_agreement/index.html"

        //首易信知乎协议
        const val SHOU_YI_XIN_PAY_AGREEMENT =
            "https://www.payeasenet.com/2.0/agreement/privacyPolicy?cashier=0dced4296927406dbaa99fbf38ba500c"

        //公众号
        const val OFFICIAL_ACCOUNTS = "${BuildConfig.QINIU_CDN}/nft_page/main/code.png"

        //联系客服
        const val CONTACT_US = "${BuildConfig.QINIU_CDN}/nft_page/setting/qrcode.png"
    }
}