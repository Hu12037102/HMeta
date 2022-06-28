package com.sbnh.comm.factory

import androidx.annotation.StringDef
import com.sbnh.comm.compat.FileCompat
import com.sbnh.comm.factory.FileFactory.TYPE_GLIDE
import com.sbnh.comm.factory.FileFactory.TYPE_APPLICATION
import com.sbnh.comm.factory.FileFactory.TYPE_HTTP
import java.io.File

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/12 9:15
 * 更新时间: 2022/6/12 9:15
 * 描述:
 */


@StringDef(TYPE_GLIDE, TYPE_APPLICATION,TYPE_HTTP)
@Retention(AnnotationRetention.SOURCE)
annotation class DirType {}
object FileFactory {
    const val TYPE_GLIDE = "glide"
    const val TYPE_APPLICATION = "app"
    const val TYPE_HTTP = "http"

    @JvmStatic
    fun createCacheDir(@DirType type: String): File? {
        return when (type) {
            TYPE_GLIDE -> {
                FileCompat.createDir(FileCompat.getCacheDir(), TYPE_GLIDE)
            }
            TYPE_APPLICATION -> {
                FileCompat.createDir(FileCompat.getCacheDir(), TYPE_APPLICATION)
            }
            TYPE_HTTP -> {
                FileCompat.createDir(FileCompat.getCacheDir(), TYPE_HTTP)
            }
            else -> null
        }
    }

    @JvmStatic
    fun createRootDir(@DirType type: String): File? {
        return when (type) {
            TYPE_GLIDE -> {
                FileCompat.createDir(FileCompat.getRootDir(), TYPE_GLIDE)
            }
            TYPE_APPLICATION -> {
                FileCompat.createDir(FileCompat.getRootDir(), TYPE_APPLICATION)
            }
            TYPE_HTTP -> {
                FileCompat.createDir(FileCompat.getRootDir(), TYPE_HTTP)
            }
            else -> {
                null
            }
        }
    }

}