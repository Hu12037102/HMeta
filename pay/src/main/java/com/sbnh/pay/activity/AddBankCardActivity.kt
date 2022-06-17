package com.sbnh.pay.activity

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.sbnh.comm.Contract
import com.sbnh.comm.base.activity.BaseCompatActivity
import com.sbnh.comm.compat.DataCompat
import com.sbnh.comm.compat.MetaViewCompat
import com.sbnh.comm.compat.NumberCompat
import com.sbnh.comm.compat.UICompat
import com.sbnh.comm.entity.base.STATUS_RUNNING
import com.sbnh.comm.other.arouter.ARouterConfig
import com.sbnh.comm.weight.click.DelayedClick
import com.sbnh.comm.weight.text.SpanTextHelper
import com.sbnh.pay.databinding.ActivityAddBankCardBinding
import com.sbnh.pay.viewmodel.AddBankCardViewModel

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/17 12:00
 * 更新时间: 2022/6/17 12:00
 * 描述:
 */
@Route(path = ARouterConfig.Path.Pay.ACTIVITY_ADD_BANK_CARD)
class AddBankCardActivity : BaseCompatActivity<ActivityAddBankCardBinding, AddBankCardViewModel>() {
    private var isAgreeAgreement = false
    override fun getViewBinding(): ActivityAddBankCardBinding =
        ActivityAddBankCardBinding.inflate(layoutInflater)

    override fun getViewModelClass(): Class<AddBankCardViewModel> = AddBankCardViewModel::class.java

    override fun initView() {
        SpanTextHelper.with()
            .append(DataCompat.getResString(com.sbnh.comm.R.string.agree))
            .append(DataCompat.getResString(com.sbnh.comm.R.string.shou_xin_pay_user_agreement))
            .setClick(MetaViewCompat.getColor(com.sbnh.comm.R.color.colorFFA56CFF)) {

            }.crete(mViewBinding.atvAgreement)

    }

    override fun initData() {

    }

    override fun initEvent() {
        mViewBinding.aivCheck.setOnClickListener(object : DelayedClick() {
            override fun onDelayedClick(v: View?) {
                isAgreeAgreement = !isAgreeAgreement
                UICompat.setImageRes(
                    mViewBinding.aivCheck,
                    if (isAgreeAgreement)
                        com.sbnh.comm.R.mipmap.icon_comm_check_agreement
                    else
                        com.sbnh.comm.R.mipmap.icon_comm_normal_agreement
                )

            }

        })
        mViewBinding.atvBinding.setOnClickListener(object : DelayedClick() {
            override fun onDelayedClick(v: View?) {
                val bankCardNumber = MetaViewCompat.getTextViewText(mViewBinding.aetBankNumber)
                if (!NumberCompat.isBankCardNumber(bankCardNumber)) {
                    showToast(com.sbnh.comm.R.string.please_input_succeed_bank_number)
                    return
                }
                val phoneNumber = MetaViewCompat.getTextViewText(mViewBinding.aetPhoneNumber)
                if (!NumberCompat.isPhoneNumber(phoneNumber)) {
                    showToast(com.sbnh.comm.R.string.please_input_sure_phone_number)
                    return
                }
                val messageCode = MetaViewCompat.getTextViewText(mViewBinding.aetMessageCode)
                if (!NumberCompat.isMessageCode(messageCode)) {
                    showToast(com.sbnh.comm.R.string.please_input_message_code)
                    return
                }
                if (!isAgreeAgreement) {
                    showToast(com.sbnh.comm.R.string.please_agree_user_agreement)
                    return
                }

            }

        })
        mViewBinding.atvGainMessageCode.setOnClickListener(object : DelayedClick() {
            override fun onDelayedClick(v: View?) {
                val phoneNumber = MetaViewCompat.getTextViewText(mViewBinding.aetPhoneNumber)
                if (!NumberCompat.isPhoneNumber(phoneNumber)) {
                    showToast(com.sbnh.comm.R.string.please_input_sure_phone_number)
                    return
                }
                mViewModel.downTimer(Contract.MESSAGE_CODE_DOWN_TIME_LENGTH)
            }

        })
    }

    override fun initObserve() {
        super.initObserve()
        mViewModel.mTimerLiveData.observe(this){
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