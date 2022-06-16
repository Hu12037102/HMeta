package com.sbnh.my.activity

import android.text.InputType
import android.view.View
import androidx.core.view.ViewCompat
import com.alibaba.android.arouter.facade.annotation.Route
import com.sbnh.comm.Contract
import com.sbnh.comm.base.activity.BaseCompatActivity
import com.sbnh.comm.compat.*
import com.sbnh.comm.entity.base.STATUS_RUNNING
import com.sbnh.comm.other.arouter.ARouterConfig
import com.sbnh.comm.other.glide.GlideCompat
import com.sbnh.comm.weight.click.DelayedClick
import com.sbnh.my.databinding.ActivitySetPaymentPasswordBinding
import com.sbnh.my.viewmodel.SetPaymentPasswordViewModel

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/16 17:22
 * 更新时间: 2022/6/16 17:22
 * 描述:设置支付密码
 */
@Route(path = ARouterConfig.Path.My.ACTIVITY_SET_PAYMENT_PASSWORD)
class SetPaymentPasswordActivity :
    BaseCompatActivity<ActivitySetPaymentPasswordBinding, SetPaymentPasswordViewModel>() {
    private var isClose = true
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
                mViewModel.downTimer(Contract.MESSAGE_CODE_DOWN_TIME_LENGTH)
            }

        })
        mViewBinding.aivCheckReadPassword.setOnClickListener {
            isClose = !isClose
            updateInputPasswordStatus()
        }
        mViewBinding.atvCommit.setOnClickListener(object : DelayedClick() {
            override fun onDelayedClick(v: View?) {
                val password = MetaViewCompat.getTextViewText(mViewBinding.aetPassword)
                if (!NumberCompat.isPayPassword(password)) {
                    showToast(com.sbnh.comm.R.string.please_six_length_password)
                    return
                }
                val messageCode = MetaViewCompat.getTextViewText(mViewBinding.atvGainMessageCode)
                if (!NumberCompat.isMessageCode(messageCode)) {
                    showToast(com.sbnh.comm.R.string.please_input_message_code)
                    return
                }
            }

        })
    }

    private fun updateInputPasswordStatus() {
        GlideCompat.loadWarpImage(
            if (isClose) com.sbnh.comm.R.mipmap.icon_my_set_payment_password_close else com.sbnh.comm.R.mipmap.icon_my_set_payment_password_open,
            mViewBinding.aivCheckReadPassword
        )
        mViewBinding.aetPassword.inputType =
                    if (isClose) {
                        InputType.TYPE_NUMBER_VARIATION_PASSWORD
                    } else {
                        InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD

                    }

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
    }
}