package com.sbnh.pay.activity

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.sbnh.comm.Contract
import com.sbnh.comm.base.activity.BaseCompatActivity
import com.sbnh.comm.compat.DataCompat
import com.sbnh.comm.compat.MetaViewCompat
import com.sbnh.comm.compat.NumberCompat
import com.sbnh.comm.compat.UICompat
import com.sbnh.comm.entity.base.BaseEntity
import com.sbnh.comm.entity.request.RequestPagerListEntity
import com.sbnh.comm.other.arouter.ARouterConfig
import com.sbnh.comm.weight.click.DelayedClick
import com.sbnh.comm.weight.text.SpanTextHelper
import com.sbnh.pay.databinding.ActivityWithdrawBinding
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
    }

    override fun initData() {
        mBalance = DataCompat.toString(intent.getStringExtra(ARouterConfig.Key.CONTENT))
        setBalance()
        if (DataCompat.isEmpty(mBalance)) {
            mViewModel.queryMyWallet()
        }
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

    }

    override fun initObserve() {
        super.initObserve()
        mViewModel.mWalletLiveData.observe(this) {
            this.mBalance = DataCompat.toString(BaseEntity.getData(it)?.balance)

        }
        mViewModel.mBankListLiveData.observe(this){

        }
    }

}