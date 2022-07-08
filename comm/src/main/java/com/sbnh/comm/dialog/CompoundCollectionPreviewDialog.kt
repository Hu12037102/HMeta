package com.sbnh.comm.dialog

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
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
    private var mAllAnimSet: AnimatorSet = AnimatorSet()

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
        initAnim()
    }

    private fun initAnim() {
        val set1 = AnimatorSet()
        val animationRotation =
            ObjectAnimator.ofFloat(mViewBinding.clContent, "rotationY", 0f, 720f)
        val scaleX = ObjectAnimator.ofFloat(mViewBinding.clContent, "scaleX", 0f, 0.7f)
        val scaleY = ObjectAnimator.ofFloat(mViewBinding.clContent, "scaleY", 0f, 0.7f)
        val alpha = ObjectAnimator.ofFloat(mViewBinding.clContent, "alpha", 0f, 1.0f)
        set1.playTogether(animationRotation, scaleX, scaleY, alpha)
        set1.duration = 1500

        set1.interpolator = AccelerateDecelerateInterpolator()
        val set2 = AnimatorSet()
        val scaleAfterX = ObjectAnimator.ofFloat(mViewBinding.clContent, "scaleX", 0.7f, 1f)
        set2.playTogether(scaleAfterX)
        val scaleAfterY = ObjectAnimator.ofFloat(mViewBinding.clContent, "scaleY", 0.7f, 1f)
        set2.playTogether(scaleAfterY)
        set2.duration = 500
        set2.interpolator = AccelerateInterpolator()
        mAllAnimSet.play(set1).before(set2)
        mAllAnimSet.addListener(mAnimatorListener)
        mAllAnimSet.start()
    }

    private val mAnimatorListener = object : Animator.AnimatorListener {
        override fun onAnimationStart(animation: Animator?) {
        }

        override fun onAnimationEnd(animation: Animator?) {
            mViewBinding.atvName.visibility = View.VISIBLE
            mViewBinding.aivClose.visibility = View.VISIBLE
        }

        override fun onAnimationCancel(animation: Animator?) {
        }

        override fun onAnimationRepeat(animation: Animator?) {
        }

    }

    override fun initData() {
        mImagePath = DataCompat.toString(arguments?.getString(ARouterConfig.Key.PICTURE_PATH))
        mContent = DataCompat.toString(arguments?.getString(ARouterConfig.Key.CONTENT))
        GlideCompat.loadWarpImage(mImagePath, mViewBinding.aivContent)
        UICompat.setText(mViewBinding.atvName, mContent)
    }

    override fun initEvent() {
        mViewBinding.aivClose.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mAllAnimSet.removeListener(mAnimatorListener)
    }
}