package com.sbnh.comm.utils

import android.util.Log
import com.sbnh.comm.compat.DataCompat
import com.sbnh.comm.config.AppConfig

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/10 11:59
 * 更新时间: 2022/6/10 11:59
 * 描述: log 工具类
 */
object LogUtils {
    private const val MAX_LOG_LENGTH = 2000
    private const val DEFAULT_VALUES = "Healer_Meta"
     const val TAG="HealerMeta"

    fun w(tag: String, msg: String? = DEFAULT_VALUES) {
        if (!AppConfig.isShowLog()) {
            return
        }
        if (msg != null) {
            if (msg.length > MAX_LOG_LENGTH) {
                var i = 0
                while (i < msg.length) {
                    if (i + MAX_LOG_LENGTH < msg.length) {
                        Log.w(tag, msg.substring(i, i + MAX_LOG_LENGTH))
                    } else {
                        Log.w(tag, msg.substring(i))
                    }
                    i += MAX_LOG_LENGTH
                }
            } else {
                Log.w(tag, msg)
            }
        } else {
            Log.w(tag, "$DEFAULT_VALUES Log is null")
        }

    }

    fun d(tag: String, msg: String? = DEFAULT_VALUES) {
        if (!AppConfig.isShowLog()) {
            return
        }
        if (msg != null) {
            if (msg.length > MAX_LOG_LENGTH) {
                var i = 0
                while (i < msg.length) {
                    if (i + MAX_LOG_LENGTH < msg.length) {
                        Log.d(tag, msg.substring(i, i + MAX_LOG_LENGTH))
                    } else {
                        Log.d(tag, msg.substring(i))
                    }
                    i += MAX_LOG_LENGTH
                }
            } else {
                Log.d(tag, msg)
            }
        } else {
            Log.d(tag, "$DEFAULT_VALUES Log is null")
        }

    }

    fun e(tag: String, msg: String? = DEFAULT_VALUES) {
        if (!AppConfig.isShowLog()) {
            return
        }
        if (msg != null) {
            if (msg.length > MAX_LOG_LENGTH) {
                var i = 0
                while (i < msg.length) {
                    if (i + MAX_LOG_LENGTH < msg.length) {
                        Log.e(tag, msg.substring(i, i + MAX_LOG_LENGTH))
                    } else {
                        Log.e(tag, msg.substring(i))
                    }
                    i += MAX_LOG_LENGTH
                }
            } else {
                Log.e(tag, msg)
            }
        } else {
            Log.e(tag, "$DEFAULT_VALUES Log is null")
        }

    }
}