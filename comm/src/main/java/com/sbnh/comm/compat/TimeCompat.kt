package com.sbnh.comm.compat

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/20 16:52
 * 更新时间: 2022/6/20 16:52
 * 描述:
 */
object TimeCompat {
    @SuppressLint("SimpleDateFormat")
    @JvmStatic
    fun getSaleTimeFormat(timestamp: Long?): String {
        var result = ""
        if (timestamp != null) {
            try {
                val format = SimpleDateFormat("MM.dd  HH:mm")
                result = format.format(Date(timestamp))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return result
    }
}