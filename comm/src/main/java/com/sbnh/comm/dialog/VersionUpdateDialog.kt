package com.sbnh.comm.dialog

import android.app.DownloadManager
import android.content.IntentFilter
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.ViewCompat
import com.alibaba.android.arouter.facade.annotation.Route
import com.sbnh.comm.Contract
import com.sbnh.comm.base.dialog.BaseCompatDialog
import com.sbnh.comm.base.dialog.BaseDataDialog
import com.sbnh.comm.compat.*
import com.sbnh.comm.databinding.DialogVersionUpdateViewBinding
import com.sbnh.comm.databinding.ItemVersionUpdateTextContentViewBinding
import com.sbnh.comm.entity.base.VersionEntity
import com.sbnh.comm.other.arouter.ARouterConfig
import com.sbnh.comm.receiver.BaseReceiver
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
    BaseDataDialog<DialogVersionUpdateViewBinding, BaseDialogViewModel>() {

    private var mVersionEntity: VersionEntity? = null
    override fun getViewBinding(): DialogVersionUpdateViewBinding =
        DialogVersionUpdateViewBinding.inflate(layoutInflater)

    override fun getViewModelClass(): Class<BaseDialogViewModel> = BaseDialogViewModel::class.java

    override fun initView() {

    }

    override fun initData() {

        mVersionEntity = arguments?.getParcelable(ARouterConfig.Key.PARCELABLE)
        ViewCompat.setBackground(mViewBinding.atvVersionName, createVersionNameDrawable())
        mVersionEntity?.let {
            UICompat.setText(mViewBinding.atvVersionName, it.version)
            for (text in it.changes) {
                var lineText = ""
                lineText += text
                if (it.changes.lastIndexOf(text) != it.changes.size - 1) {
                    lineText += "\n"
                }
                val textContentViewBinding =
                    ItemVersionUpdateTextContentViewBinding.inflate(
                        layoutInflater,
                        mViewBinding.llContent,
                        true
                    )
                UICompat.setText(textContentViewBinding.atvContent, lineText)
            }
        }


    }

    private fun createVersionNameDrawable(): Drawable {
        val drawable = GradientDrawableCompat.create()
        drawable.setColor(MetaViewCompat.getColor(com.sbnh.comm.R.color.colorFF632681))
        drawable.cornerRadius = PhoneCompat.dp2px(requireContext(), Contract.DP.VALUE_50F).toFloat()
        return drawable
    }


    override fun initEvent() {
        mViewBinding.atvUpload.setOnClickListener(object : DelayedClick() {
            override fun onDelayedClick(v: View?) {
                val isSucceed = DownloadFileTool.get()
                    .downloadFile(DataCompat.toString(mVersionEntity?.downUrls))
                if (isSucceed) {
                    showToast(com.sbnh.comm.R.string.download_ing_notify_read)
                }
                MetaViewCompat.setClickViewEnable(mViewBinding.atvUpload, false)
                UICompat.setText(mViewBinding.atvUpload, com.sbnh.comm.R.string.download_ing)
                mOnDialogItemInfoClickListener?.onClickConfirm(v)
            }

        })
        mViewBinding.root.setOnClickListener {
            dismiss()
        }
    }


}