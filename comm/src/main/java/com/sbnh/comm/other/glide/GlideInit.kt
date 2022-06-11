package com.sbnh.comm.other.glide

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import okhttp3.OkHttpClient

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/11 21:47
 * 更新时间: 2022/6/11 21:47
 * 描述:
 */
@GlideModule(glideName = "HealerMetaGlide")
class GlideInit :AppGlideModule(){
    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        super.registerComponents(context, glide, registry)

    }

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        super.applyOptions(context, builder)
    }
}