package com.sbnh.comm.compat

import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/12 20:21
 * 更新时间: 2022/6/12 20:21
 * 描述:
 */
object ViewCompat {
    @JvmStatic
    fun setViewSize(
        view: View?,
        width: Int = ViewGroup.LayoutParams.MATCH_PARENT,
        height: Int = ViewGroup.LayoutParams.MATCH_PARENT
    ) {
        if (view == null) {
            return
        }
        var params = view.layoutParams
        if (DataCompat.isNull(params)) {
            params = ViewGroup.LayoutParams(width, height)
        } else {
            params.width = width
            params.height = height
        }
        view.layoutParams = params
    }

    @JvmStatic
    @ColorInt
    fun getColor(@ColorRes colorRes: Int): Int =
        ContextCompat.getColor(DataCompat.getContext(), colorRes)

    @JvmStatic
    fun getDrawable(@DrawableRes drawableRes: Int): Drawable? =
        ContextCompat.getDrawable(DataCompat.getContext(), drawableRes)

    @JvmStatic
    fun setClickButton(view: View?) {
        if (DataCompat.notNull(view)) {
            ViewCompat.setBackground(view!!, GradientDrawableCompat.createClickDrawable())
        }
    }
}