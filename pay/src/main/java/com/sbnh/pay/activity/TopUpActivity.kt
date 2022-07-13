package com.sbnh.pay.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.sbnh.comm.base.activity.BaseCompatActivity
import com.sbnh.comm.compat.DataCompat
import com.sbnh.comm.other.arouter.ARouterConfig
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
    }
}