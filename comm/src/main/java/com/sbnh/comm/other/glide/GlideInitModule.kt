package com.sbnh.comm.other.glide

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Priority
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.load.engine.executor.GlideExecutor
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import com.sbnh.comm.R
import com.sbnh.comm.factory.FileFactory
import com.sbnh.comm.other.glide.GlideInitModule.Companion.HEALER_META_GLIDE
import java.io.InputStream

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/11 21:47
 * 更新时间: 2022/6/11 21:47
 * 描述:
 */
@GlideModule(glideName = HEALER_META_GLIDE)
class GlideInitModule : AppGlideModule() {
    companion object {
        const val HEALER_META_GLIDE = "HealerMetaGlide"
        private const val MAX_MEMORY_CACHE_SIZE = 25 * 1024 * 1024L
        private const val MAX_BITMAP_POOL_SIZE = 30 * 1024 * 1024L
        private const val DISK_CACHE_FILE_SIZE = 200 * 1024 * 1024L
    }

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        registry.replace(GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory())
    }

    override fun isManifestParsingEnabled(): Boolean {
        return false
    }

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        val defaultOption = createDefaultOptions()
        builder.setDefaultRequestOptions(defaultOption)
            .setMemoryCache(LruResourceCache(MAX_MEMORY_CACHE_SIZE))
            .setBitmapPool(LruBitmapPool(MAX_BITMAP_POOL_SIZE))
            .setDiskCacheExecutor(GlideExecutor.newDiskCacheExecutor())
            .setDiskCache(
                DiskLruCacheFactory(
                    FileFactory.createCacheDir(FileFactory.TYPE_GLIDE)?.absolutePath,
                    DISK_CACHE_FILE_SIZE
                )
                //  InternalCacheDiskCacheFactory(BaseApplication.mContext
            )
            .setLogLevel(Log.WARN)
            .setSourceExecutor(GlideExecutor.newSourceExecutor())
    }

    private fun createDefaultOptions(): RequestOptions {
        return RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .skipMemoryCache(false)
            .centerCrop()
            .encodeQuality(80)
            .format(DecodeFormat.PREFER_ARGB_8888)
            .placeholder(R.color.colorPlaceholder)
            .encodeFormat(Bitmap.CompressFormat.JPEG)
            .disallowHardwareConfig()
            // .dontAnimate()
            .priority(Priority.LOW)
        //.signature()

    }
}