package com.sbnh.comm.other.glide

import android.content.Context
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideContext
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
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
    fun loadWarpImage(
        any: Any?,
        imageView: ImageView?,
        width: Int = 200,
        height: Int = width
    ) {
        loadFitCenterImage(any, imageView, width, height)
        //  Glide.with(imageView).load("").override()
    }

    @JvmStatic
    fun createGlide(
        any: Any,
        context: Context
    ): GlideRequest<*> {
        return HealerMetaGlide.with(context).load(any)
    }

    @JvmStatic
    fun loadRoundWarpImage(any: Any?, imageView: ImageView?, radius: Int) {
        if (any == null || imageView == null)
            return
        val options = RequestOptions().transform(FitCenter(), RoundedCorners(radius))
        HealerMetaGlide.with(imageView).load(any).apply(options).into(imageView)
    }

}