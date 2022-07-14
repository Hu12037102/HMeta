package com.sbnh.my.activity

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.activity.result.ActivityResult
import com.alibaba.android.arouter.facade.annotation.Route
import com.sbnh.comm.base.activity.BaseCompatActivity
import com.sbnh.comm.compat.UICompat
import com.sbnh.comm.entity.base.BaseEntity
import com.sbnh.comm.other.arouter.ARouterConfig
import com.sbnh.comm.other.arouter.ARouters
import com.sbnh.comm.other.glide.GlideCompat
import com.sbnh.comm.utils.LogUtils
import com.sbnh.comm.weight.click.DelayedClick
import com.sbnh.my.databinding.ActivityMyWalletBinding
import com.sbnh.my.viewmodel.MyWalletViewModel
import com.scwang.smart.refresh.layout.api.RefreshLayout

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/7/4 10:27
 * 更新时间: 2022/7/4 10:27
 * 描述:
 */
@Route(path = ARouterConfig.Path.My.ACTIVITY_MY_WALLET)
class MyWalletActivity : BaseCompatActivity<ActivityMyWalletBinding, MyWalletViewModel>() {
    override fun getViewBinding(): ActivityMyWalletBinding =
        ActivityMyWalletBinding.inflate(layoutInflater)

    override fun getViewModelClass(): Class<MyWalletViewModel> = MyWalletViewModel::class.java

    override fun initView() {
        GlideCompat.loadWarpImage(
            com.sbnh.comm.R.mipmap.icon_my_wallet_background,
            mViewBinding.aivContent
        )
        setWalletBalance()
    }

    override fun initData() {
        loadSmartData()
    }


    override fun loadSmartData(refreshLayout: RefreshLayout?, isRefresh: Boolean) {
        super.loadSmartData(refreshLayout, isRefresh)
        mViewModel.queryMyWallet()
    }

    override fun initEvent() {
        mViewBinding.atvWithdraw.setOnClickListener(object : DelayedClick() {
            override fun onDelayedClick(v: View?) {
                //  ARouters.startActivity(ARouterConfig.Path.Pay.ACTIVITY_WITHDRAW)
                val intent =
                    ARouters.intent(this@MyWalletActivity, ARouterConfig.Path.Pay.ACTIVITY_WITHDRAW)
                startActivityForResult(intent)
            }

        })
        mViewBinding.pvBill.setOnClickListener(object : DelayedClick() {
            override fun onDelayedClick(v: View?) {
            }

        })
        mViewBinding.pvBankCard.setOnClickListener(object : DelayedClick() {
            override fun onDelayedClick(v: View?) {
                ARouters.startActivity(ARouterConfig.Path.Pay.ACTIVITY_BANK_CARD_LIST)
            }

        })
        mViewBinding.pvTransactionPassword.setOnClickListener(object : DelayedClick() {
            override fun onDelayedClick(v: View?) {
                ARouters.startActivity(ARouterConfig.Path.My.ACTIVITY_SET_PAYMENT_PASSWORD)
            }

        })
        mViewBinding.atvRecharge.setOnClickListener(object : DelayedClick() {
            override fun onDelayedClick(v: View?) {
              //  ARouters.startActivity(ARouterConfig.Path.Pay.ACTIVITY_TOP_UP)
                val intent =
                    ARouters.intent(this@MyWalletActivity, ARouterConfig.Path.Pay.ACTIVITY_TOP_UP)
                startActivityForResult(intent)
            }

        })
    }

    override fun initObserve() {
        super.initObserve()
        mViewModel.mWalletLiveData.observe(this) {
            val entity = BaseEntity.getData(it)
            setWalletBalance(entity?.balance)
        }
    }

    private fun setWalletBalance(balance: String? = null) {
        UICompat.setText(mViewBinding.atvMoney, "￥${balance ?: 0.0}")
    }

    override fun onActivityResultCallback(result: ActivityResult) {
        super.onActivityResultCallback(result)
        LogUtils.w("onActivityResultCallback--", "$result")
        if (result.resultCode == Activity.RESULT_OK) {
            loadSmartData()
        }
    }
}