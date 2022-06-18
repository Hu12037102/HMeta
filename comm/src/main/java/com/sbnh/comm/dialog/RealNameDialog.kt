package com.sbnh.comm.dialog

import android.graphics.drawable.Drawable
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import com.sbnh.comm.base.dialog.BaseCompatDialog
import com.sbnh.comm.compat.*
import com.sbnh.comm.databinding.DialogRealNameBinding
import com.sbnh.comm.viewmodel.BaseDialogViewModel
import com.sbnh.comm.weight.click.DelayedClick

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/17 17:55
 * 更新时间: 2022/6/17 17:55
 * 描述:
 */
class RealNameDialog : BaseCompatDialog<DialogRealNameBinding, BaseDialogViewModel>() {
    override fun getViewBinding(): DialogRealNameBinding =
        DialogRealNameBinding.inflate(
            layoutInflater
        )

    override fun getViewModelClass(): Class<BaseDialogViewModel> = BaseDialogViewModel::class.java

    override fun initView() {
        ViewCompat.setBackground(mViewBinding.clParent, createWindowBackground())
    }

    override fun initData() {
    }

    override fun initEvent() {
        mViewBinding.atvCommit.setOnClickListener(object : DelayedClick() {
            override fun onDelayedClick(v: View?) {
                val name = MetaViewCompat.getTextViewText(mViewBinding.aetName)
                if (DataCompat.isEmpty(name)) {
                    showToast(com.sbnh.comm.R.string.please_inout_name)
                    return
                }
                val idCard = MetaViewCompat.getTextViewText(mViewBinding.aetIdCard)
                if (!NumberCompat.isIdCard(idCard)) {
                    showToast(com.sbnh.comm.R.string.please_inout_id_card_number)
                    return
                }

            }

        })
        mViewBinding.aivClose.setOnClickListener(object : DelayedClick() {
            override fun onDelayedClick(v: View?) {
                dismiss()
            }

        })
    }

    override fun getGravity(): Int = Gravity.CENTER
    private fun createWindowBackground(): Drawable {
        val drawable = GradientDrawableCompat.create()
        drawable.setColor(MetaViewCompat.getColor(com.sbnh.comm.R.color.colorFF191919))
        drawable.cornerRadius = PhoneCompat.dp2px(DataCompat.getContext(), 14f).toFloat()
        return drawable
    }
}