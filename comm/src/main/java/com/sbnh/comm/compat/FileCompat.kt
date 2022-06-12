package com.sbnh.comm.compat

import android.app.Application
import androidx.core.content.ContextCompat
import com.sbnh.comm.base.BaseApplication
import java.io.File

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/10 16:02
 * 更新时间: 2022/6/10 16:02
 * 描述:
 */
object FileCompat {
    @JvmStatic
    fun getRootDir(): File? {
        val context = BaseApplication.getContext()
        val files = ContextCompat.getExternalCacheDirs(context)
        //    var dir: File? = null
        return if (files.isNotEmpty()) {
            files[0]
        } else {
            context.getExternalFilesDir(null)
        }

    }

    @JvmStatic
    fun getCacheDir(): File? {
        val context = BaseApplication.getContext()
        val files = ContextCompat.getExternalCacheDirs(context)
        return if (files.isNotEmpty()) {
            files[0]
        } else {
            val dir = context.externalCacheDir
            dir ?: context.cacheDir
        }
    }

    @JvmStatic
    fun createDir(fileParent: File?, childDirName: String): File? {
        var childFile: File? = null
        if (DataCompat.isNull(fileParent))
            return childFile
        if (fileParent!!.exists() && fileParent.isDirectory) {
            childFile = File(fileParent, childDirName)
            if (!childFile.exists()) {
                childFile.mkdirs()
            }
        }
        return childFile
    }

}