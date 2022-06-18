package com.sbnh.comm.http

import java.net.HttpURLConnection

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/18 13:51
 * 更新时间: 2022/6/18 13:51
 * 描述:
 */
class ApiService {
    object Path {
        const val USER_LOGIN = "/healer_nft/user/login"
        const val GAIN_MESSAGE_CODE="/healer_nft/user/validate_code"
        const val REGISTER="/healer_nft/user/registered"
    }

    object HttpCode {
        const val SUCCEED = HttpURLConnection.HTTP_OK
        const val CLIENT_ERROR = HttpURLConnection.HTTP_BAD_REQUEST
        const val SERVICE_ERROR = HttpURLConnection.HTTP_INTERNAL_ERROR
    }
}