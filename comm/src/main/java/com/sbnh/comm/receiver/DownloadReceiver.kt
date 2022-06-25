package com.sbnh.comm.receiver

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.text.TextUtils
import com.sbnh.comm.Contract
import com.sbnh.comm.compat.DataCompat
import com.sbnh.comm.tool.DownloadFileTool
import com.sbnh.comm.utils.LogUtils

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/25 19:06
 * 更新时间: 2022/6/25 19:06
 * 描述:
 */
class DownloadReceiver : BaseReceiver() {
    private var mOnDownloadCallback: OnDownloadCallback? = null
    fun setOnDownloadCallback(onDownloadCallback: OnDownloadCallback?) {
        this.mOnDownloadCallback = onDownloadCallback
    }

    /**
     * 获取下载状态
     *
     * @param downloadId an ID for the download, unique across the system.
     *                   This ID is used to make future calls related to this download.
     * @return int
     * @see DownloadManager#STATUS_PENDING  　　 下载等待开始时
     * @see DownloadManager#STATUS_PAUSED   　　 下载暂停
     * @see DownloadManager#STATUS_RUNNING　     正在下载中　
     * @see DownloadManager#STATUS_SUCCESSFUL   下载成功
     * @see DownloadManager#STATUS_FAILED       下载失败
     */
    override fun onReceive(context: Context?, intent: Intent?) {

        if (TextUtils.equals(intent?.action, DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
            val downloadId =
                intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, Contract.UNKNOWN_LONG_VALUE)
            if (downloadId == null || downloadId == Contract.UNKNOWN_LONG_VALUE) {
                mOnDownloadCallback?.onDownloadError()
                return
            }
            val uri = DownloadFileTool.getDownloadManger()?.getUriForDownloadedFile(downloadId)
            if (uri != null) {
                mOnDownloadCallback?.onDownloadComplete(uri)
            } else {
                mOnDownloadCallback?.onDownloadError()
            }
            LogUtils.w("onReceive---", "${uri}")

        }


    }

    interface OnDownloadCallback {
        fun onDownloadComplete(uri: Uri)
        fun onDownloadError()
    }
}