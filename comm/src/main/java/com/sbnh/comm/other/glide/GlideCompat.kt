package com.sbnh.comm.other.glide

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.sbnh.comm.R

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/11 21:45
 * 更新时间: 2022/6/11 21:45
 * 描述:
 */
object GlideCompat {

    private fun checkNullToString(any: Any?): Any {
        return any ?: ""
    }

    @JvmStatic
    fun loadImage(any: Any?, imageView: ImageView?) {
        if (any == null || imageView == null) {
            return
        }
        HealerMetaGlide.with(imageView).load(any).into(imageView)
    }

    @JvmStatic
    fun loadImage(
        any: Any?,
        imageView: ImageView?,
        @DrawableRes res: Int = R.color.colorPlaceholder
    ) {
        if (any == null || imageView == null) {
            return
        }
        HealerMetaGlide.with(imageView).load(any).placeholder(res).into(imageView)
    }

    @JvmStatic
    fun loadFitCenterImage(
        any: Any?,
        imageView: ImageView?,
    ) {
        if (any == null || imageView == null) {
            return
        }
        HealerMetaGlide.with(imageView).load(any).fitCenter().into(imageView)
    }

}