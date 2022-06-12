package com.sbnh.comm.compat

import android.util.TypedValue
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/10 15:09
 * 更新时间: 2022/6/10 15:09
 * 描述:
 */
object UICompat {
    @JvmStatic
    fun setText(textView: TextView?, text: CharSequence? = "") {
        textView?.text = text
    }

    @JvmStatic
    fun setTextSize(textView: TextView?, sizeSp: Float) {
        textView?.setTextSize(TypedValue.COMPLEX_UNIT_PX, sizeSp)
    }

    @JvmStatic
    fun setImageRes(imageView: ImageView?, @DrawableRes resDrawable: Int?) {
        imageView?.setImageResource(resDrawable ?: 0)
    }

    @JvmStatic
    fun setTextColor(textView: TextView?, @ColorInt textColor: Int) {
        textView?.setTextColor(textColor)
    }

    @JvmStatic
    fun setTextColorRes(textView: TextView?, @ColorRes textColor: Int) {
        textView?.setTextColor(ViewCompat.getColor(textColor))
    }
}