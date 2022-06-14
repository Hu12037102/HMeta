package com.sbnh.comm.other.glide

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
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
        imageView: ImageView?
    ) {
        if (any == null || imageView == null) {
            return
        }
        HealerMetaGlide.with(imageView).load(any).centerInside().into(imageView)
    }

    @JvmStatic
    fun loadFitCenterImage(
        any: Any?,
        imageView: ImageView?,
        width: Int = 200,
        height: Int = width
    ) {
        if (any == null || imageView == null) {
            return
        }
        HealerMetaGlide.with(imageView).load(any).centerInside().override(width, height)
            .into(imageView)
    }

    @JvmStatic
    fun loadWarpImage(
        any: Any?,
        imageView: ImageView?
    ) {
        loadFitCenterImage(any, imageView, Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
        //  Glide.with(imageView).load("").override()
    }

    @JvmStatic
    fun createGlide(
        any: Any,
        imageView: ImageView
    ): GlideRequest<*> {
        return HealerMetaGlide.with(imageView).load(any)
    }

}