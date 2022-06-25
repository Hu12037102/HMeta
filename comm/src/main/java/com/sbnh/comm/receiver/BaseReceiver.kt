package com.sbnh.comm.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/25 19:06
 * 更新时间: 2022/6/25 19:06
 * 描述:
 */
abstract class BaseReceiver : BroadcastReceiver() {
    companion object {
        @JvmStatic
        fun registerReceiver(
            context: Context?,
            receiver: BroadcastReceiver,
            intentFilter: IntentFilter
        ) {
            try {
                context?.registerReceiver(receiver, intentFilter)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        @JvmStatic
        fun unRegisterReceiver(context: Context?, receiver: BroadcastReceiver) {
            try {
                context?.unregisterReceiver(receiver)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}