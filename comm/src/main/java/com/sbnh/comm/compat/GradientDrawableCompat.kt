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
        return drawable
    }

    @JvmStatic
    fun createClickDrawable(dpValue: Float): GradientDrawable {
        val drawable = create()
        drawable.cornerRadius = PhoneCompat.dp2px(DataCompat.getContext(), dpValue).toFloat()
        drawable.colors = intArrayOf(
            MetaViewCompat.getColor(R.color.colorFF4A25BB),
            MetaViewCompat.getColor(R.color.colorFFA24DD0)
        )
        drawable.orientation = GradientDrawable.Orientation.LEFT_RIGHT
        return drawable
    }

    @JvmStatic
    fun createLoginInputDrawable(): GradientDrawable {
        val drawable = create()
        drawable.cornerRadius = PhoneCompat.dp2px(DataCompat.getContext(), 50f).toFloat()
        drawable.setColor(MetaViewCompat.getColor(R.color.colorFF2C2E4C))
        return drawable
    }
}