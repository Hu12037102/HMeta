package com.sbnh.comm.dialog

import com.alibaba.android.arouter.facade.annotation.Route
import com.sbnh.comm.R
import com.sbnh.comm.base.dialog.BaseDataDialog
import com.sbnh.comm.compat.DataCompat
import com.sbnh.comm.compat.UICompat
import com.sbnh.comm.databinding.DialogComppundCollectionPreviewBinding
import com.sbnh.comm.other.arouter.ARouterConfig
import com.sbnh.comm.other.arouter.ARouters
import com.sbnh.comm.other.glide.GlideCompat
import com.sbnh.comm.viewmodel.BaseDialogViewModel

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/7/8 10:26
 * 更新时间: 2022/7/8 10:26
 * 描述:
 */
@Route(path = ARouterConfig.Path.Comm.DIALOG_COMPOUND_COLLECTION_PREVIEW)
class CompoundCollectionPreviewDialog :
    BaseDataDialog<DialogComppundCollectionPreviewBinding, BaseDialogViewModel>() {

    companion object {
        @JvmStatic
        fun create(imagePath: String, content: String): CompoundCollectionPreviewDialog? {
            val dialog = ARouters.build(ARouterConfig.Path.Comm.DIALOG_COMPOUND_COLLECTION_PREVIEW)
                .withString(ARouterConfig.Key.PICTURE_PATH, imagePath)
                .withString(ARouterConfig.Key.CONTENT, content)
                .navigation()
            return if (dialog is CompoundCollectionPreviewDialog) dialog else null
        }
    }

    private var mImagePath = ""
    private var mContent = ""
    override fun getViewBinding(): DialogComppundCollectionPreviewBinding =
        DialogComppundCollectionPreviewBinding.inflate(layoutInflater)

    override fun getViewModelClass(): Class<BaseDialogViewModel> = BaseDialogViewModel::class.java

    override fun initView() {
        GlideCompat.loadWarpImage(
            R.mipmap.icon_compound_collection_preview_background,
            mViewBinding.aivBackground
        )
    }

    override fun initData() {
        mImagePath = DataCompat.toString(arguments?.getString(ARouterConfig.Key.PICTURE_PATH))
        mContent = DataCompat.toString(arguments?.getString(ARouterConfig.Key.CONTENT))
        GlideCompat.loadWarpImage(mImagePath, mViewBinding.aivContent)
        UICompat.setText(mViewBinding.atvName,mContent)
    }

    override fun initEvent() {
        mViewBinding.aivClose.setOnClickListener {
            dismiss()
        }
    }
}