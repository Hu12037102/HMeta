package com.sbnh.pay.activity

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.sbnh.comm.Contract
import com.sbnh.comm.base.activity.BaseCompatActivity
import com.sbnh.comm.base.callback.OnRecyclerItemClickListener
import com.sbnh.comm.compat.*
import com.sbnh.comm.dialog.SetPaymentPasswordDialog
import com.sbnh.comm.entity.base.BaseEntity
import com.sbnh.comm.entity.pay.BankCardEntity
import com.sbnh.comm.entity.request.RequestPagerListEntity
import com.sbnh.comm.entity.request.RequestWithdrawBankCardEntity
import com.sbnh.comm.other.arouter.ARouterConfig
import com.sbnh.comm.other.arouter.ARouters
import com.sbnh.comm.weight.click.DelayedClick
import com.sbnh.comm.weight.text.SpanTextHelper
import com.sbnh.pay.adapter.WithdrawBankCardAdapter
import com.sbnh.pay.databinding.ActivityWithdrawBinding
import com.sbnh.pay.databinding.ItemFootBankCardListViewBinding
import com.sbnh.pay.databinding.ItemFootSelectorBankCardViewBinding
import com.sbnh.pay.databinding.ItemSelectorBankCardViewBinding
import com.sbnh.pay.viewmodel.WithdrawViewModel

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/7/4 11:55
 * 更新时间: 2022/7/4 11:55
 * 描述:提现
 */
@Route(path = ARouterConfig.Path.Pay.ACTIVITY_WITHDRAW)
class WithdrawActivity : BaseCompatActivity<ActivityWithdrawBinding, WithdrawViewModel>() {
    private var mBalance: String = Contract.DEFAULT_STRING_VALUE
    private var mBankCardAdapter: WithdrawBankCardAdapter? = null
    private var mCheckBankCardEntity: BankCardEntity? = null
    private val mBankCardData = ArrayList<BankCardEntity>()
    private var mFootAddBankCardViewBinding: ItemFootSelectorBankCardViewBinding? = null
    override fun getViewBinding(): ActivityWithdrawBinding =
        ActivityWithdrawBinding.inflate(layoutInflater)

    override fun getViewModelClass(): Class<WithdrawViewModel> = WithdrawViewModel::class.java

    override fun initView() {

        SpanTextHelper.with()
            .append(DataCompat.getResString(com.sbnh.comm.R.string.cash_withdrawal_amount))
            .setSize(16, true)
            .setColor(MetaViewCompat.getColor(com.sbnh.comm.R.color.colorWhite))
            .append(DataCompat.getResString(com.sbnh.comm.R.string.yuan))
            .setSize(12, true)
            .setColor(MetaViewCompat.getColor(com.sbnh.comm.R.color.colorWhite))
            .crete(mViewBinding.atvWithdrawMoneyTitle)
        mViewBinding.rvBankCard.layoutManager = LinearLayoutManager(this)
        initFootView()
    }

    private fun initFootView() {
        mFootAddBankCardViewBinding =
            ItemFootSelectorBankCardViewBinding.inflate(
                layoutInflater,
                mViewBinding.rvBankCard,
                false
            )
        UICompat.setText(
            mFootAddBankCardViewBinding?.atvContent,
            DataCompat.getResString(com.sbnh.comm.R.string.add_bank_card_withdraw)
        )
    }

    override fun initData() {
        mBalance = DataCompat.toString(intent.getStringExtra(ARouterConfig.Key.CONTENT))
        setBalance()
        if (DataCompat.isEmpty(mBalance)) {
            mViewModel.queryMyWallet()
        }
        mBankCardAdapter = WithdrawBankCardAdapter(this, mBankCardData)
        mFootAddBankCardViewBinding?.root?.let {
            mBankCardAdapter?.addFootView(it)
        }
        mViewBinding.rvBankCard.adapter = mBankCardAdapter
        mViewModel.loadBankCardList(RequestPagerListEntity())
    }

    private fun setBalance() {
        //  val numberMoney = NumberCompat.string2Double(mBalance)
        UICompat.setText(
            mViewBinding.atvBalance,
            com.sbnh.comm.R.string.balance_s,
            DataCompat.getBalanceFormat(mBalance)
        )
    }

    override fun initEvent() {
        mViewBinding.clParent.setOnClickListener(object : DelayedClick() {
            override fun onDelayedClick(v: View?) {
                MetaViewCompat.hideSoftKeyBoard(mViewBinding.aetMoney)
            }

        })
        mFootAddBankCardViewBinding?.root?.setOnClickListener(object : DelayedClick() {
            override fun onDelayedClick(v: View?) {
                ARouters.startActivity(ARouterConfig.Path.Pay.ACTIVITY_ADD_BANK_CARD)
            }

        })
        mBankCardAdapter?.setOnRecyclerItemClickListener(object : OnRecyclerItemClickListener {
            override fun onClickItem(view: View?, position: Int) {
                this@WithdrawActivity.mCheckBankCardEntity = mBankCardData[position]
            }

        })
        mViewBinding.atvCommit.setOnClickListener(object : DelayedClick() {
            override fun onDelayedClick(v: View?) {
                if (MetaViewCompat.textViewTextIsEmpty(mViewBinding.aetMoney)) {
                    showToast(com.sbnh.comm.R.string.please_input_withdraw_money)
                    return
                }
                val numberBalanceMoney = NumberCompat.string2Double(mBalance)
                if (numberBalanceMoney < Contract.MIN_WITHDRAW_MONEY) {
                    showToast(com.sbnh.comm.R.string.insufficient_wallet_balance)
                    return
                }
                val numberInputMoney = NumberCompat.string2Double(
                    DataCompat.toString(
                        MetaViewCompat.getTextViewText(mViewBinding.aetMoney)
                    )
                )
                if (numberInputMoney < Contract.MIN_WITHDRAW_MONEY) {
                    showToast(com.sbnh.comm.R.string.withdraw_money_must_greater)
                    return
                }
                if (numberInputMoney > numberBalanceMoney) {
                    showToast(com.sbnh.comm.R.string.withdrawal_money_greater_balance)
                    return
                }

                if (mCheckBankCardEntity == null) {
                    showToast(com.sbnh.comm.R.string.please_selector_withdraw_bank_card)
                    return
                }

                showPayPasswordDialog(numberInputMoney)

            }

        })
    }

    private fun showPayPasswordDialog(money: Double) {
        val dialog = SetPaymentPasswordDialog(
            DataCompat.getResString(com.sbnh.comm.R.string.withdrawal_bank_card),
            DataCompat.getResString(com.sbnh.comm.R.string.please_inout_payment_password_check_identity)
        )
        DialogCompat.showFragmentDialog(dialog, supportFragmentManager)
        dialog.setOnInputPasswordCallback(object :
            SetPaymentPasswordDialog.OnInputPasswordCallback {
            override fun onComplete(password: String) {
                mViewModel.withdrawBankCard(
                    RequestWithdrawBankCardEntity(
                        DataCompat.toString(mCheckBankCardEntity?.bindId),
                        password,
                        money
                    )
                )
                dialog.dismiss()
            }

        })
    }

    override fun initObserve() {
        super.initObserve()
        mViewModel.mWalletLiveData.observe(this) {
            this.mBalance = DataCompat.toString(BaseEntity.getData(it)?.balance)

        }
        mViewModel.mBankListLiveData.observe(this) {
            val data = BaseEntity.getPagerData(it)
            UICompat.notifyAdapterAddDateChanged(null, mBankCardAdapter, true, mBankCardData, data)

        }
        mViewModel.mWithdrawBankCardLiveData.observe(this) {
            MetaViewCompat.finishActivitySetResult(this)
            showToast(com.sbnh.comm.R.string.withdrawal_bank_card_succeed_please_look)
        }
    }

}