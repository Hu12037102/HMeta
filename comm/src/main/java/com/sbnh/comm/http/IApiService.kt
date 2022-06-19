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
        const val EXIT_LOGIN="healer_nft/user/login_out"
    }

    object HttpCode {
        const val SUCCEED = HttpURLConnection.HTTP_OK
        const val CLIENT_ERROR = HttpURLConnection.HTTP_BAD_REQUEST
        const val SERVICE_ERROR = HttpURLConnection.HTTP_INTERNAL_ERROR
    }

    object Key {
        const val CONTENT_TYPE = "Content-Type";
        const val CHARSET = "charset";
        const val ACCESS_TOKEN = "accessToken";
        const val TS = "ts";
        const val UUID = "uuid";
        const val SID = "sid";
    }
}