package com.sbnh.comm.compat

import android.annotation.SuppressLint
import com.sbnh.comm.Contract
import com.sbnh.comm.utils.LogUtils
import java.text.SimpleDateFormat
import java.util.*

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/20 16:52
 * 更新时间: 2022/6/20 16:52
 * 描述:
 */
object TimeCompat {
    private const val ONE_MINUTE = 60

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

    @SuppressLint("SimpleDateFormat")
    @JvmStatic
    fun getTimeFormat(timestamp: Long?, format: String = Contract.DEFAULT_TIME_FORMAT): String {
        var result = ""
        if (timestamp != null) {
            try {
                result = SimpleDateFormat(format).format(Date(timestamp))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return result
    }

    @JvmStatic
    fun timesToMinuteSecond(time: Long): Array<Long> {
        val array: Array<Long> = arrayOf(0, 0)
        //  val time = timeMillisecond / 1000
        if (time > ONE_MINUTE) {
            val minute = time / ONE_MINUTE
            array[0] = minute
            array[1] = time % ONE_MINUTE
        } else {
            array[0] = 0
            array[1] = time
        }
        LogUtils.w("timesToMinuteSecond--", "${array[0]}--${array[1]}")
        return array
    }
}