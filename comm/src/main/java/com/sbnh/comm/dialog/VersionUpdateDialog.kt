package com.sbnh.comm.dialog

import android.app.DownloadManager
import android.content.IntentFilter
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.sbnh.comm.base.dialog.BaseCompatDialog
import com.sbnh.comm.compat.*
import com.sbnh.comm.databinding.DialogVersionUpdateViewBinding
import com.sbnh.comm.entity.base.VersionEntity
import com.sbnh.comm.other.arouter.ARouterConfig
import com.sbnh.comm.receiver.DownloadReceiver
import com.sbnh.comm.tool.DownloadFileTool
import com.sbnh.comm.viewmodel.BaseDialogViewModel
import com.sbnh.comm.weight.click.DelayedClick

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/25 16:54
 * 更新时间: 2022/6/25 16:54
 * 描述:
 */
@Route(path = ARouterConfig.Path.Comm.DIALOG_VERSION_UPDATE)
class VersionUpdateDialog :
    BaseCompatDialog<DialogVersionUpdateViewBinding, BaseDialogViewModel>() {
    private val mReceiver = DownloadReceiver()
    private var mVersionEntity: VersionEntity? = null
    override fun getViewBinding(): DialogVersionUpdateViewBinding =
        DialogVersionUpdateViewBinding.inflate(layoutInflater)

    override fun getViewModelClass(): Class<BaseDialogViewModel> = BaseDialogViewModel::class.java

    override fun initView() {

    }

    override fun initData() {

        mVersionEntity = arguments?.getParcelable(ARouterConfig.Key.PARCELABLE)
        UICompat.setText(mViewBinding.atvContent, DataCompat.toString(mVersionEntity?.changes))
    }

    override fun initEvent() {
        mViewBinding.atvUpload.setOnClickListener(object : DelayedClick() {
            override fun onDelayedClick(v: View?) {
                context?.registerReceiver(
                    mReceiver,
                    IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
                )
                val isSucceed = DownloadFileTool.get()
                    .downloadFile(DataCompat.toString(mVersionEntity?.downUrls))
                if (isSucceed) {
                    showToast(com.sbnh.comm.R.string.download_ing_notify_read)
                }
                MetaViewCompat.setClickViewEnable(mViewBinding.atvUpload, false)
                UICompat.setText(mViewBinding.atvUpload, com.sbnh.comm.R.string.download_ing)
                // ARoutersActivity.startBrowserActivity(context, mVersionEntity?.downUrls)
            }

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        context?.unregisterReceiver(mReceiver)
    }
}