package com.sbnh.comm.compat

import android.graphics.drawable.GradientDrawable
import com.sbnh.comm.R

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/14 18:09
 * 更新时间: 2022/6/14 18:09
 * 描述:
 */
object GradientDrawableCompat {
    @JvmStatic
    fun create(): GradientDrawable {
        val drawable = GradientDrawable()
        drawable.shape = GradientDrawable.RECTANGLE
        // drawable.isAutoMirrored = true
        return drawable
    }

    @JvmStatic
    fun createClickDrawable(): GradientDrawable {
        val drawable = create()
        drawable.cornerRadius = PhoneCompat.dp2px(DataCompat.getContext(), 8f).toFloat()
        drawable.colors = intArrayOf(
            ViewCompat.getColor(R.color.colorFF4A25BB),
            ViewCompat.getColor(R.color.colorFFA24DD0)
        )
        return drawable
    }
}