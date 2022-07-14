package com.sbnh.pay.activity

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.sbnh.comm.Contract
import com.sbnh.comm.base.activity.BaseCompatActivity
import com.sbnh.comm.base.dialog.BaseDataDialog
import com.sbnh.comm.compat.*
import com.sbnh.comm.dialog.InputMessageCodeDialog
import com.sbnh.comm.entity.base.BaseEntity
import com.sbnh.comm.entity.pay.BankCardEntity
import com.sbnh.comm.entity.request.RequestPayOrderAfterEntity
import com.sbnh.comm.entity.request.RequestTopUpAfterEntity
import com.sbnh.comm.entity.request.RequestTopUpBeforeEntity
import com.sbnh.comm.other.arouter.ARouterConfig
import com.sbnh.comm.other.arouter.ARouters
import com.sbnh.comm.weight.click.DelayedClick
import com.sbnh.comm.weight.text.SpanTextHelper
import com.sbnh.pay.databinding.ActivityTopUpBinding
import com.sbnh.pay.viewmodel.TopUpViewModel

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/7/13 16:54
 * 更新时间: 2022/7/13 16:54
 * 描述: 充值
 */
@Route(path = ARouterConfig.Path.Pay.ACTIVITY_TOP_UP)
class TopUpActivity : BaseCompatActivity<ActivityTopUpBinding, TopUpViewModel>() {
    override fun getViewBinding(): ActivityTopUpBinding =
        ActivityTopUpBinding.inflate(layoutInflater)

    private var mBankCardEntity: BankCardEntity? = null

    override fun getViewModelClass(): Class<TopUpViewModel> = TopUpViewModel::class.java

    override fun initView() {
        SpanTextHelper.with().append(DataCompat.getResString(com.sbnh.comm.R.string.top_up_money))
            .setSize(16, true)
            .append(DataCompat.getResString(com.sbnh.comm.R.string.yuan))
            .setSize(12, true)
            .crete(mViewBinding.atvMoneyTitle)
    }

    override fun initData() {
    }

    override fun initEvent() {
        mViewBinding.atvCommit.setOnClickListener(object : DelayedClick() {
            override fun onDelayedClick(v: View?) {
                val money = MetaViewCompat.getTextViewText(mViewBinding.aetMoney)
                if (DataCompat.isEmpty(money)) {
                    showToast(com.sbnh.comm.R.string.please_input_top_up_money)
                    return
                }
                val moneyNumber = NumberCompat.string2Double(DataCompat.toString(money))
                if (moneyNumber < Contract.MIN_TOP_UP_MONEY) {
                    showToast(com.sbnh.comm.R.string.top_up_money_must_greater)
                    return
                }
                if (mBankCardEntity == null) {
                    showToast(com.sbnh.comm.R.string.please_selector_pay_way)
                    return
                }
                mViewModel.topUpMoneyBefore(
                    RequestTopUpBeforeEntity(
                        moneyNumber,
                        mBankCardEntity?.bindId
                    )
                )

            }

        })
        mViewBinding.clParent.setOnClickListener {
            MetaViewCompat.hideSoftKeyBoard(mViewBinding.aetMoney)
        }
        mViewBinding.clBankCardPay.setOnClickListener(object : DelayedClick() {
            override fun onDelayedClick(v: View?) {
                val selectorBankCardDialog =
                    ARouters.build(ARouterConfig.Path.Pay.DIALOG_SELECTOR_BANK_CARD)
                        .withString(
                            ARouterConfig.Key.ID,
                            mBankCardEntity?.id
                        ).navigation() as BaseDataDialog<*, *>
                selectorBankCardDialog.setOnCallbackValues(object :
                    BaseDataDialog.OnCallbackValues {
                    override fun onValue(obj: Any) {
                        if (obj is BankCardEntity) {
                            mBankCardEntity = obj
                            UICompat.setImageRes(
                                mViewBinding.aivBankCardPay,
                                com.sbnh.comm.R.mipmap.icon_comm_check
                            )
                        } else {
                            mBankCardEntity = null
                        }
                        selectorBankCardDialog.dismiss()
                    }

                })
                DialogCompat.showFragmentDialog(
                    selectorBankCardDialog,
                    supportFragmentManager
                )
            }

        })
    }

    override fun initObserve() {
        super.initObserve()
        mViewModel.mTopUpBeforeLiveData.observe(this) {
            val data = BaseEntity.getData(it)
            val dialog =
                ARouters.build(ARouterConfig.Path.Comm.DIALOG_INPUT_MESSAGE_CODE).withString(
                    ARouterConfig.Key.CONTENT,
                    DataCompat.toString(mBankCardEntity?.mobile)
                ).navigation()
            if (dialog is InputMessageCodeDialog) {
                DialogCompat.showFragmentDialog(dialog, supportFragmentManager)
                dialog.setOnCallbackValues(object : BaseDataDialog.OnCallbackValues {
                    override fun onValue(obj: Any) {
                        if (obj is CharSequence) {
                            mViewModel.topUpMoneyAfter(
                                RequestTopUpAfterEntity(
                                    DataCompat.toString(
                                        obj
                                    ),
                                    DataCompat.toString(data?.paymentOrderId),
                                    DataCompat.toString(data?.requestId)
                                )
                            )
                        }
                        dialog.dismiss()
                    }
                })
            }
        }
        mViewModel.mTopUpAfterLiveData.observe(this) {
            MetaViewCompat.finishActivitySetResult(this)
            showToast(com.sbnh.comm.R.string.top_up_succeed)
        }
    }


}