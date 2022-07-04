package com.sbnh.pay.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.sbnh.comm.base.activity.BaseCompatActivity
import com.sbnh.comm.other.arouter.ARouterConfig
import com.sbnh.pay.databinding.ActivityWithdrawBinding
import com.sbnh.pay.viewmodel.WithdrawViewModel

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/7/4 11:55
 * 更新时间: 2022/7/4 11:55
 * 描述:
 */
@Route(path = ARouterConfig.Path.Pay.ACTIVITY_WITHDRAW)
class WithdrawActivity : BaseCompatActivity<ActivityWithdrawBinding, WithdrawViewModel>() {
    override fun getViewBinding(): ActivityWithdrawBinding =
        ActivityWithdrawBinding.inflate(layoutInflater)

    override fun getViewModelClass(): Class<WithdrawViewModel> = WithdrawViewModel::class.java

    override fun initView() {

    }

    override fun initData() {
    }

    override fun initEvent() {
    }

}