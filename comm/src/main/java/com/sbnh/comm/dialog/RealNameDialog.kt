package com.sbnh.comm.dialog

import android.graphics.drawable.Drawable
import android.view.Gravity
import android.view.View
import androidx.core.view.ViewCompat
import androidx.lifecycle.lifecycleScope
import com.sbnh.comm.base.dialog.BaseCompatDialog
import com.sbnh.comm.compat.*
import com.sbnh.comm.databinding.DialogRealNameBinding
import com.sbnh.comm.dialog.viewmodel.RealNameDialogViewModel
import com.sbnh.comm.entity.other.CaptchaCheckResultEntity
import com.sbnh.comm.entity.request.RequestRealNameEmpty
import com.sbnh.comm.info.UserInfoStore
import com.sbnh.comm.other.tencent.CaptchaDialogHelper
import com.sbnh.comm.weight.click.DelayedClick
import kotlinx.coroutines.launch

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/17 17:55
 * 更新时间: 2022/6/17 17:55
 * 描述:
 */
class RealNameDialog : BaseCompatDialog<DialogRealNameBinding, RealNameDialogViewModel>() {
    override fun getViewBinding(): DialogRealNameBinding =
        DialogRealNameBinding.inflate(
            layoutInflater
        )

    override fun getViewModelClass(): Class<RealNameDialogViewModel> =
        RealNameDialogViewModel::class.java

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
                CaptchaDialogHelper.showDialog(requireContext(),
                    object : CaptchaDialogHelper.OnDialogCallback {
                        override fun onResult(entity: CaptchaCheckResultEntity?) {
                            if (CaptchaCheckResultEntity.isSucceed(entity)) {
                                mViewModel.realNameAuthentication(
                                    RequestRealNameEmpty(
                                        DataCompat.toString(name),
                                        DataCompat.toString(idCard),
                                        entity?.ticket,
                                        entity?.randstr
                                    )
                                )
                            }
                        }

                    })

            }

        })
        mViewBinding.aivClose.setOnClickListener(object : DelayedClick() {
            override fun onDelayedClick(v: View?) {
                dismiss()
            }

        })
    }

    override fun initObserve() {
        super.initObserve()
        mViewModel.mRealNameLiveData.observe(this) {
            lifecycleScope.launch {
                UserInfoStore.get().putRealName(true)
                showToast(com.sbnh.comm.R.string.real_name_authentication_succeed)
                dismiss()

            }
        }
    }

    override fun getGravity(): Int = Gravity.CENTER
    private fun createWindowBackground(): Drawable {
        val drawable = GradientDrawableCompat.create()
        drawable.setColor(MetaViewCompat.getColor(com.sbnh.comm.R.color.colorFF191919))
        drawable.cornerRadius = PhoneCompat.dp2px(DataCompat.getContext(), 14f).toFloat()
        return drawable
    }
}