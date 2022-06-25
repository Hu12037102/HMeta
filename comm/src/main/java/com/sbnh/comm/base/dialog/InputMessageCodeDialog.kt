package com.sbnh.comm.base.dialog

import com.alibaba.android.arouter.facade.annotation.Route
import com.huxiaobai.inputedit.weight.InputEditTextView
import com.sbnh.comm.compat.*
import com.sbnh.comm.databinding.DialogInputMessageCodeViewBinding
import com.sbnh.comm.entity.base.UserInfoEntity
import com.sbnh.comm.info.UserInfoStore
import com.sbnh.comm.other.arouter.ARouterConfig
import com.sbnh.comm.viewmodel.BaseDialogViewModel
import com.sbnh.comm.weight.text.SpanTextHelper

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/25 9:56
 * 更新时间: 2022/6/25 9:56
 * 描述:
 */
@Route(path = ARouterConfig.Path.Comm.DIALOG_INPUT_MESSAGE_CODE)
class InputMessageCodeDialog :
    BaseDataDialog<DialogInputMessageCodeViewBinding, BaseDialogViewModel>() {
    override fun getViewBinding(): DialogInputMessageCodeViewBinding =
        DialogInputMessageCodeViewBinding.inflate(layoutInflater)

    override fun getViewModelClass(): Class<BaseDialogViewModel> = BaseDialogViewModel::class.java

    override fun initView() {
    }

    override fun initData() {
        mViewModel.loadUserInfo()
    }

    override fun initEvent() {
        mViewBinding.etvCode.setOnTextInputTextCallback(object :
            InputEditTextView.OnTextInputTextCallback {
            override fun onComplete(text: CharSequence) {
                mOnCallbackValues?.onValue(text)
            }

        })
        mViewBinding.aivClose.setOnClickListener {
            dismiss()
        }
    }


    override fun resultUserInfo(userInfoEntity: UserInfoEntity?) {
        if (UserInfoStore.isLogin(userInfoEntity)) {
            SpanTextHelper.with()
                .append(DataCompat.getResString(com.sbnh.comm.R.string.message_code_send_phone_number))
                .append(NumberCompat.encryptPhoneNumber(userInfoEntity?.mobile))
                .setColor(MetaViewCompat.getColor(com.sbnh.comm.R.color.colorFFF4CD9A))
                .crete(mViewBinding.atvContent)
        }
    }


}