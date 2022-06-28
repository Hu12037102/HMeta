package com.sbnh.my.activity

import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.facade.annotation.Route
import com.sbnh.comm.Contract
import com.sbnh.comm.base.activity.BaseCompatActivity
import com.sbnh.comm.compat.*
import com.sbnh.comm.entity.base.STATUS_RUNNING
import com.sbnh.comm.entity.other.CaptchaCheckResultEntity
import com.sbnh.comm.entity.request.RequestMessageCodeEntity
import com.sbnh.comm.entity.request.RequestSetPaymentPasswordEntity
import com.sbnh.comm.info.UserInfoStore
import com.sbnh.comm.other.arouter.ARouterConfig
import com.sbnh.comm.other.glide.GlideCompat
import com.sbnh.comm.other.tencent.CaptchaDialogHelper
import com.sbnh.comm.weight.click.DelayedClick
import com.sbnh.my.databinding.ActivitySetPaymentPasswordBinding
import com.sbnh.my.viewmodel.SetPaymentPasswordViewModel
import kotlinx.coroutines.launch

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/16 17:22
 * 更新时间: 2022/6/16 17:22
 * 描述:设置支付密码
 */
@Route(path = ARouterConfig.Path.My.ACTIVITY_SET_PAYMENT_PASSWORD)
class SetPaymentPasswordActivity :
    BaseCompatActivity<ActivitySetPaymentPasswordBinding, SetPaymentPasswordViewModel>() {
    private var isPassword = true
    override fun getViewBinding(): ActivitySetPaymentPasswordBinding =
        ActivitySetPaymentPasswordBinding.inflate(layoutInflater)

    override fun getViewModelClass(): Class<SetPaymentPasswordViewModel> =
        SetPaymentPasswordViewModel::class.java

    override fun initView() {
        MetaViewCompat.setClickButton(mViewBinding.atvCommit, Contract.DP.VALUE_8F)

    }

    override fun initData() {

    }

    override fun initEvent() {
        mViewBinding.atvGainMessageCode.setOnClickListener(object : DelayedClick() {
            override fun onDelayedClick(v: View?) {
                CaptchaDialogHelper.showDialog(this@SetPaymentPasswordActivity,
                    object : CaptchaDialogHelper.OnDialogCallback {
                        override fun onResult(entity: CaptchaCheckResultEntity?) {
                            if (CaptchaCheckResultEntity.isSucceed(entity)) {
                                lifecycleScope.launch {
                                    val phone = UserInfoStore.get().getMobile()
                                    mViewModel.gainMessageCode(
                                        RequestMessageCodeEntity(
                                            phone,
                                            entity?.randstr,
                                            entity?.ticket
                                        )
                                    )
                                }
                            }
                        }

                    })
                //
            }

        })
        mViewBinding.aivCheckReadPassword.setOnClickListener {
            isPassword = !isPassword
            updateInputPasswordStatus()
        }
        mViewBinding.atvCommit.setOnClickListener(object : DelayedClick() {
            override fun onDelayedClick(v: View?) {
                val password = MetaViewCompat.getTextViewText(mViewBinding.aetPassword)
                if (!NumberCompat.isPayPassword(password)) {
                    showToast(com.sbnh.comm.R.string.please_six_length_password)
                    return
                }
                val messageCode = MetaViewCompat.getTextViewText(mViewBinding.aetMessageCode)
                if (!NumberCompat.isMessageCode(messageCode)) {
                    showToast(com.sbnh.comm.R.string.please_input_message_code)
                    return
                }
                mViewModel.setPaymentPassword(
                    RequestSetPaymentPasswordEntity(
                        DataCompat.toString(
                            password
                        ), DataCompat.toString(messageCode)
                    )
                )
            }

        })
    }

    private fun updateInputPasswordStatus() {
        GlideCompat.loadWarpImage(
            if (isPassword) com.sbnh.comm.R.mipmap.icon_my_set_payment_password_close else com.sbnh.comm.R.mipmap.icon_my_set_payment_password_open,
            mViewBinding.aivCheckReadPassword
        )
        mViewBinding.aetPassword.transformationMethod =
            if (isPassword)
                PasswordTransformationMethod.getInstance()
            else
                HideReturnsTransformationMethod.getInstance()

        val text = MetaViewCompat.getTextViewText(mViewBinding.aetPassword)
        mViewBinding.aetPassword.setSelection(DataCompat.getTextLength(text))
        //  MetaViewCompat.selectorTextViewEnd(mViewBinding.aetPassword)
    }

    override fun initObserve() {
        super.initObserve()
        mViewModel.mTimerLiveData.observe(this) {
            if (it.status == STATUS_RUNNING) {
                mViewBinding.atvGainMessageCode.isEnabled = false
                UICompat.setText(
                    mViewBinding.atvGainMessageCode, "${it.lastTimeLength}s"
                )
            } else {
                mViewBinding.atvGainMessageCode.isEnabled = true
                UICompat.setText(
                    mViewBinding.atvGainMessageCode, DataCompat.getResString(
                        com.sbnh.comm.R.string.send_message_code
                    )
                )
            }
        }

        mViewModel.mPaymentPasswordLiveData.observe(this) {
            showToast(com.sbnh.comm.R.string.set_payment_password_succeed)
            MetaViewCompat.finishActivity(this)
        }
    }

    override fun resultGainMessageCode() {
        super.resultGainMessageCode()
        mViewModel.downTimer(Contract.MESSAGE_CODE_DOWN_TIME_LENGTH)
    }
}