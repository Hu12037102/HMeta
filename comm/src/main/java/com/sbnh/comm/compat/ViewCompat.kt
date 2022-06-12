package com.sbnh.comm.compat

import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.sbnh.comm.base.BaseApplication

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
        ContextCompat.getColor(BaseApplication.mContext, colorRes)
}