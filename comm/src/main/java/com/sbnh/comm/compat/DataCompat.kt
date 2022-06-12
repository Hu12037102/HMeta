package com.sbnh.comm.compat

import android.content.Context
import android.text.TextUtils
import androidx.annotation.DimenRes

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/10 12:05
 * 更新时间: 2022/6/10 12:05
 * 描述:
 */
object DataCompat {
    @JvmStatic
    fun isEmpty(text: CharSequence?): Boolean {
        return TextUtils.isEmpty(text)
    }

    @JvmStatic
    fun checkNotNull(text: CharSequence?, defaultText: CharSequence = ""): CharSequence {
        return if (isEmpty(text)) defaultText else text!!
    }

    @JvmStatic
    fun checkNullToString(any: Any?): Any {
        return any ?: ""
    }

    @JvmStatic
    fun dimen2Int(context: Context, @DimenRes dimenRes: Int): Float =
        context.resources.getDimension(dimenRes)

    @JvmStatic
    fun <T> notNull(t: T?): Boolean = t != null

    @JvmStatic
    fun <T> isNull(t: T?): Boolean = t == null
}