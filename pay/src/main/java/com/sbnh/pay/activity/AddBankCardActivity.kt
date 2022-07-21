package com.sbnh.pay.activity

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.sbnh.comm.Contract
import com.sbnh.comm.base.activity.BaseCompatActivity
import com.sbnh.comm.compat.DataCompat
import com.sbnh.comm.compat.MetaViewCompat
import com.sbnh.comm.compat.NumberCompat
import com.sbnh.comm.compat.UICompat
import com.sbnh.comm.entity.base.BaseEntity
import com.sbnh.comm.entity.base.STATUS_RUNNING
import com.sbnh.comm.entity.other.CaptchaCheckResultEntity
import com.sbnh.comm.entity.pay.NumberQueryBankCardInfoEntity
import com.sbnh.comm.entity.request.RequestBankCardInfoEntity
import com.sbnh.comm.entity.request.RequestBindingBankCardAfterEntity
import com.sbnh.comm.entity.request.RequestBindingBankCardBeforeEntity
import com.sbnh.comm.http.IApiService
import com.sbnh.comm.other.arouter.ARouterConfig
import com.sbnh.comm.other.arouter.ARoutersActivity
import com.sbnh.comm.other.glide.GlideCompat
import com.sbnh.comm.other.tencent.CaptchaDialogHelper
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
    companion object {
        const val QUERY_CARD_INFO_LENGTH = 6
    }

    private var mBankEntity: NumberQueryBankCardInfoEntity? = null
    private var isAgreeAgreement = false
    private var mBindingBankCardId: String? = ""
    override fun getViewBinding(): ActivityAddBankCardBinding =
        ActivityAddBankCardBinding.inflate(layoutInflater)

    override fun getViewModelClass(): Class<AddBankCardViewModel> = AddBankCardViewModel::class.java

    override fun initView() {
        SpanTextHelper.with()
            .append(DataCompat.getResString(com.sbnh.comm.R.string.agree))
            .append(DataCompat.getResString(com.sbnh.comm.R.string.shou_xin_pay_user_agreement))
            .setClick(MetaViewCompat.getColor(com.sbnh.comm.R.color.colorFFA56CFF)) {
                ARoutersActivity.startWebContentActivity(IApiService.H5.SHOU_YI_XIN_PAY_AGREEMENT)
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
                        com.sbnh.comm.R.mipmap.icon_comm_check
                    else
                        com.sbnh.comm.R.mipmap.icon_comm_normal
                )

            }

        })
        mViewBinding.atvBinding.setOnClickListener(object : DelayedClick() {
            override fun onDelayedClick(v: View?) {
                val bankCardNumber = MetaViewCompat.getTextViewText(mViewBinding.aetBankCardNumber,true)
                if (!NumberCompat.isBankCardNumber(bankCardNumber)) {
                    showToast(com.sbnh.comm.R.string.please_input_succeed_bank_number)
                    return
                }
                val phoneNumber = MetaViewCompat.getTextViewText(mViewBinding.aetPhoneNumber,true)
                if (!NumberCompat.isPhoneNumber(phoneNumber)) {
                    showToast(com.sbnh.comm.R.string.please_input_sure_phone_number)
                    return
                }
                val messageCode = MetaViewCompat.getTextViewText(mViewBinding.aetMessageCode,true)
                if (!NumberCompat.isMessageCode(messageCode)) {
                    showToast(com.sbnh.comm.R.string.please_input_message_code)
                    return
                }
                if (!isAgreeAgreement) {
                    showToast(com.sbnh.comm.R.string.please_agree_user_other_pay_agreement)
                    return
                }
                mViewModel.bindingBankCardAfter(
                    RequestBindingBankCardAfterEntity(
                        DataCompat.toString(
                            mBindingBankCardId
                        ), DataCompat.toString(messageCode)
                    )
                )

            }

        })
        mViewBinding.atvGainMessageCode.setOnClickListener(object : DelayedClick() {
            override fun onDelayedClick(v: View?) {
                val phoneNumber = MetaViewCompat.getTextViewText(mViewBinding.aetPhoneNumber,true)
                if (!NumberCompat.isPhoneNumber(phoneNumber)) {
                    showToast(com.sbnh.comm.R.string.please_input_sure_phone_number)
                    return
                }
                CaptchaDialogHelper.showDialog(this@AddBankCardActivity,
                    object : CaptchaDialogHelper.OnDialogCallback {
                        override fun onResult(entity: CaptchaCheckResultEntity?) {
                            if (CaptchaCheckResultEntity.isSucceed(entity)) {
                                val bankCardNumber =
                                    MetaViewCompat.getTextViewText(mViewBinding.aetBankCardNumber,true)
                                if (mBankEntity == null) {
                                    mViewModel.queryBankCardInfo(
                                        RequestBankCardInfoEntity(
                                            DataCompat.toString(
                                                bankCardNumber
                                            )
                                        ), true
                                    )
                                } else {
                                    mViewModel.bindingBankCardBefore(
                                        RequestBindingBankCardBeforeEntity(
                                            mBankEntity?.preCardNo,
                                            DataCompat.toString(bankCardNumber),
                                            DataCompat.toString(phoneNumber)
                                        )
                                    )
                                }

                            }
                        }

                    })
                //
            }

        })
        mViewBinding.aetBankCardNumber.addTextChangedListener(mAetBankCardNumberTextWatcher)
        mViewBinding.aetPhoneNumber.addTextChangedListener(mPhoneNumberWatcher)
    }

    private val mAetBankCardNumberTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            UICompat.setBankCardNumberEditText(mViewBinding.aetBankCardNumber,s,start, before)
            val length = MetaViewCompat.getTextViewLength(mViewBinding.aetBankCardNumber,true)
            val text =
                DataCompat.toString(MetaViewCompat.getTextViewText(mViewBinding.aetBankCardNumber),true)
            if (length >= QUERY_CARD_INFO_LENGTH) {
                if (mBankEntity == null) {
                    mViewModel.queryBankCardInfo(RequestBankCardInfoEntity(text))
                }
            } else if (length < QUERY_CARD_INFO_LENGTH) {
                UICompat.setImageDrawable(mViewBinding.civBankIcon, null)
                UICompat.setText(mViewBinding.atvBankName, null)
                mBankEntity = null
            }

        }

        override fun afterTextChanged(s: Editable?) {
        }

    }
    private val mPhoneNumberWatcher = object :TextWatcher{
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            UICompat.setPhoneEditText(mViewBinding.aetPhoneNumber,s,start, before)
        }

        override fun afterTextChanged(s: Editable?) {
        }

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
        mViewModel.mQueryBankCardLiveData.observe(this) {
            this.mBankEntity = BaseEntity.getData(it)
            GlideCompat.loadImage(mBankEntity?.logo, mViewBinding.civBankIcon)
            UICompat.setText(mViewBinding.atvBankName, mBankEntity?.name)

            if (mBankEntity?.isBindingBankCardBefore == true) {
                mViewModel.bindingBankCardBefore(
                    RequestBindingBankCardBeforeEntity(
                        mBankEntity?.preCardNo,
                        DataCompat.toString(MetaViewCompat.getTextViewText(mViewBinding.aetBankCardNumber,true)),
                        DataCompat.toString(MetaViewCompat.getTextViewText(mViewBinding.aetPhoneNumber,true))
                    )
                )
            }
        }
        mViewModel.mGainMessageCodeLiveData.observe(this) {
            mViewModel.downTimer(Contract.MESSAGE_CODE_DOWN_TIME_LENGTH)
        }
        mViewModel.mBindingBankCardBeforeLiveData.observe(this) {
            mBindingBankCardId = BaseEntity.getData(it)
            mViewModel.downTimer(Contract.MESSAGE_CODE_DOWN_TIME_LENGTH)
            showToast(com.sbnh.comm.R.string.gain_message_code_succeed)
            /*  val messageCode = MetaViewCompat.getTextViewText(mViewBinding.aetMessageCode)
              mViewModel.bindingBankCardAfter(
                  RequestBindingBankCardAfterEntity(
                      DataCompat.toString(
                          entity
                      ), DataCompat.toString(messageCode)
                  )
              )*/
        }
        mViewModel.mBindingBankCardAfterLiveData.observe(this) {
            MetaViewCompat.finishActivitySetResult(this)
            showToast(com.sbnh.comm.R.string.add_bank_card_succeed)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mViewBinding.aetBankCardNumber.removeTextChangedListener(mAetBankCardNumberTextWatcher)
        mViewBinding.aetPhoneNumber.removeTextChangedListener(mPhoneNumberWatcher)
    }
}