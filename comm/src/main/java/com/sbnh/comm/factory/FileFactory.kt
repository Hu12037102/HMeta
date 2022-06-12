package com.sbnh.comm.factory

import androidx.annotation.StringDef
import com.sbnh.comm.compat.FileCompat
import com.sbnh.comm.factory.FileFactory.TYPE_GLIDE
import java.io.File

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/12 9:15
 * 更新时间: 2022/6/12 9:15
 * 描述:
 */


@StringDef(TYPE_GLIDE)
@Retention(AnnotationRetention.SOURCE)
annotation class DirType {}
object FileFactory {
    const val TYPE_GLIDE = "glide"


    @JvmStatic
    fun createCacheDir(@DirType type: String, ): File? {
        return when (type) {
            TYPE_GLIDE -> {
                FileCompat.createDir(FileCompat.getCacheDir(),TYPE_GLIDE)
            }
            else -> null
        }
    }

}