package com.sbnh.comm.tool

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import com.sbnh.comm.Contract
import com.sbnh.comm.compat.DataCompat
import com.sbnh.comm.compat.FileCompat
import com.sbnh.comm.compat.ToastCompat
import com.sbnh.comm.factory.FileFactory
import java.io.File

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/25 18:31
 * 更新时间: 2022/6/25 18:31
 * 描述:
 */
class DownloadFileTool private constructor() {


    companion object {
        private val mTool: DownloadFileTool by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { DownloadFileTool() }

        @JvmStatic
        fun get(): DownloadFileTool {
            return mTool
        }

        @JvmStatic
        fun getDownloadManger(): DownloadManager? {
            val downloadManger = DataCompat.getContext().getSystemService(Context.DOWNLOAD_SERVICE)
            return if (downloadManger is DownloadManager) {
                downloadManger
            } else {
                null
            }
        }
    }

    init {
        initRequest()
    }

    private fun initRequest() {

    }

    fun downloadFile(
        path: String,
        dir: String = "apk",
        fileName: String = "${FileCompat.createFileOnlyName()}.apk"
    ): Boolean {
        val downloadManger =
            getDownloadManger()
        val request = DownloadManager.Request(Uri.parse(path))
            .setAllowedOverMetered(true)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
            .setTitle(DataCompat.getResString(com.sbnh.comm.R.string.app_name))
            .setDescription(DataCompat.getResString(com.sbnh.comm.R.string.download_ing))
            .setDestinationInExternalFilesDir(
                DataCompat.getContext(),
                dir,
                fileName
            )
            .setAllowedOverRoaming(true)
        val id = downloadManger?.enqueue(request)
        return (id ?: Contract.UNKNOWN_LONG_VALUE) > 0L

    }

}