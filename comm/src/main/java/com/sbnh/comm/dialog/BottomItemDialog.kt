package com.sbnh.comm.dialog

import android.app.Dialog
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.core.view.ViewCompat
import com.sbnh.comm.base.dialog.BaseCompatDialog
import com.sbnh.comm.base.interfaces.OnDialogItemInfoClickListener
import com.sbnh.comm.base.viewmodel.BaseViewModel
import com.sbnh.comm.compat.GradientDrawableCompat
import com.sbnh.comm.compat.MetaViewCompat
import com.sbnh.comm.compat.PhoneCompat
import com.sbnh.comm.compat.UICompat
import com.sbnh.comm.databinding.DialogBottomItemViewBinding
import com.sbnh.comm.utils.LogUtils
import com.sbnh.comm.viewmodel.BaseDialogViewModel
import com.sbnh.comm.weight.click.DelayedClick

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/23 16:09
 * 更新时间: 2022/6/23 16:09
 * 描述:
 */
class BottomItemDialog(var title: String = "") :
    BaseCompatDialog<DialogBottomItemViewBinding, BaseDialogViewModel>() {
    private var onDialogItemInfoClickListener: OnDialogItemInfoClickListener? = null
    fun setOnDialogItemInfoClickListener(onDialogItemInfoClickListener: OnDialogItemInfoClickListener?) {
        this.onDialogItemInfoClickListener = onDialogItemInfoClickListener
    }

    override fun getViewBinding(): DialogBottomItemViewBinding =
        DialogBottomItemViewBinding.inflate(layoutInflater)

    override fun getViewModelClass(): Class<BaseDialogViewModel> = BaseDialogViewModel::class.java

    override fun initView() {
        ViewCompat.setBackground(mViewBinding.atvTitle, createTitleBackground())
        isCancelable = true
        LogUtils.w("initView--", "$isCancelable----$mDialog")
    }

    /*  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
          return super.onCreateDialog(savedInstanceState).apply {
              setCancelable(false)
              setCanceledOnTouchOutside(false)
          }
      }*/

    override fun initData() {
        UICompat.setText(mViewBinding.atvTitle, title)
    }

    override fun initEvent() {
        mViewBinding.root.setOnClickListener(object : DelayedClick() {
            override fun onDelayedClick(v: View?) {
                dismiss()
            }

        })
        mViewBinding.atvCancel.setOnClickListener(object : DelayedClick() {
            override fun onDelayedClick(v: View?) {
                onDialogItemInfoClickListener?.onClickCancel(v)
            }

        })
        mViewBinding.atvTitle.setOnClickListener(object : DelayedClick() {
            override fun onDelayedClick(v: View?) {
                onDialogItemInfoClickListener?.onClickConfirm(v)
            }

        })

    }

    private fun createTitleBackground(): Drawable {
        val drawable = GradientDrawableCompat.create()
        drawable.cornerRadii = floatArrayOf(
            PhoneCompat.dp2px(requireContext(), 12f).toFloat(),
            PhoneCompat.dp2px(requireContext(), 12f).toFloat(),
            PhoneCompat.dp2px(requireContext(), 12f).toFloat(),
            PhoneCompat.dp2px(requireContext(), 12f).toFloat(),
            0f, 0f,
            0f, 0f
        )
        drawable.setColor(MetaViewCompat.getColor(com.sbnh.comm.R.color.colorFF2E2E2E))

        return drawable
    }

    override fun getGravity(): Int = Gravity.BOTTOM
}