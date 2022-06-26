package com.sbnh.comm.compat

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import androidx.core.content.ContextCompat
import java.io.File
import java.util.*

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/10 16:02
 * 更新时间: 2022/6/10 16:02
 * 描述:
 */
object FileCompat {
    @JvmStatic
    fun getRootDir(): File? {
        val context = DataCompat.getContext()
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
        val context = DataCompat.getContext()
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

    @JvmStatic
    fun createFileOnlyName(): String {
        var name = ""
        var uuid = DataCompat.toString(UUID.randomUUID().toString())
        uuid = uuid.replace("-", "")
        if (uuid.length > 8) {
            uuid = uuid.substring(uuid.length - 8)
        }
        name += uuid
        var timestamp = "${System.currentTimeMillis()}"
        if (timestamp.length > 8) {
            timestamp = timestamp.substring(timestamp.length - 8)
        }
        name += timestamp
        return name
    }

    @JvmStatic
    fun findPathByUri(context: Context, uri: Uri): String {
        var path = ""
        return try {
            val resolver = context.contentResolver
            val cursor =
                resolver.query(uri, arrayOf(MediaStore.MediaColumns.DATA), null, null, null)
            if (cursor != null && cursor.count > 0) {
                cursor.moveToFirst()
                path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                cursor.close()
            }
            path
        } catch (e: Exception) {
            e.printStackTrace()
            path
        }

    }
}