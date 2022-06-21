package com.sbnh.order.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.sbnh.comm.base.activity.BaseCompatActivity
import com.sbnh.comm.other.arouter.ARouterConfig
import com.sbnh.order.databinding.ActivityOrderDetailsBinding
import com.sbnh.order.viewmodel.OrderDetailsViewModel

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/21 9:20
 * 更新时间: 2022/6/21 9:20
 * 描述: 订单详情页面
 */
@Route(path = ARouterConfig.Path.Order.ACTIVITY_ORDER_DETAILS)
class OrderDetailsActivity :
    BaseCompatActivity<ActivityOrderDetailsBinding, OrderDetailsViewModel>() {
    private var mOrderId: String = ""
    override fun getViewBinding(): ActivityOrderDetailsBinding =
        ActivityOrderDetailsBinding.inflate(layoutInflater)

    override fun getViewModelClass(): Class<OrderDetailsViewModel> =
        OrderDetailsViewModel::class.java

    override fun initView() {
        mOrderId = intent.getStringExtra(ARouterConfig.Key.ID) ?: ""
    }

    override fun initData() {
        mViewModel.queryOrderDetails(mOrderId)
    }

    override fun initEvent() {
    }

    override fun initObserve() {
        super.initObserve()
        mViewModel.mOrderDetailsLiveData.observe(this) {

        }
    }
}