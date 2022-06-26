package com.sbnh.comm.compat

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.IntentFilter

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/10 17:12
 * 更新时间: 2022/6/10 17:12
 * 描述:
 */
object PhoneCompat {
    @JvmStatic
    fun screenWidth(context: Context) = context.resources.displayMetrics.widthPixels

    @JvmStatic
    fun screenHeight(context: Context) = context.resources.displayMetrics.heightPixels

    @JvmStatic
    fun density(context: Context): Float = context.resources.displayMetrics.density

    @JvmStatic
    fun dp2px(context: Context, dpValue: Float): Int {
        return (density(context) * dpValue + 0.5f).toInt()
    }

    @JvmStatic
    fun px2dp(context: Context, pxValue: Float): Int {
        return (pxValue / density(context) + 0.5f).toInt()
    }

    @JvmStatic
    fun copyText(text:CharSequence) {
        val service = DataCompat.getContext().getSystemService(Context.CLIPBOARD_SERVICE)
        if (service is ClipboardManager){
            service.setPrimaryClip(ClipData.newPlainText(null,text))
        }
    }

}