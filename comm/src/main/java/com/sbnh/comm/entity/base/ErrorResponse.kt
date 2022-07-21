package com.sbnh.comm.entity.base

import com.sbnh.comm.R
import com.sbnh.comm.compat.DataCompat

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/18 16:08
 * 更新时间: 2022/6/18 16:08
 * 描述:
 */
data class ErrorResponse(val error: String?, var message: String?) {
    companion object {
        @JvmStatic
        fun create() = ErrorResponse(
            DataCompat.getResString(R.string.default_http_error),
            DataCompat.getResString(R.string.default_http_error)
        )
    }
}