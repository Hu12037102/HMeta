package com.sbnh.order.activity

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.sbnh.comm.base.activity.BaseCompatActivity
import com.sbnh.comm.compat.*
import com.sbnh.comm.entity.base.BaseEntity
import com.sbnh.comm.entity.base.STATUS_RUNNING
import com.sbnh.comm.entity.order.*
import com.sbnh.comm.other.arouter.ARouterConfig
import com.sbnh.comm.other.glide.GlideCompat
import com.sbnh.comm.utils.LogUtils
import com.sbnh.order.databinding.ActivityOrderDetailsBinding
import com.sbnh.order.viewmodel.OrderDetailsViewModel
import kotlin.math.abs

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
        mViewBinding.clContent.visibility = View.GONE
    }

    override fun initData() {
        mViewModel.queryOrderDetails(mOrderId)
    }

    override fun initEvent() {
    }

    override fun initObserve() {
        super.initObserve()
        mViewModel.mOrderDetailsLiveData.observe(this) {
            val data = BaseEntity.getData(it)
            data?.let { entity ->

                when (entity.status) {
                    STATUS_WAIT_PAY -> {
                        mViewBinding.clContent.visibility = View.VISIBLE
                        mViewBinding.clStatusWaitPay.visibility = View.VISIBLE
                        mViewBinding.clStatusOther.visibility = View.GONE
                        val outTime = entity.orderTimeOut ?: 0
                        val lastSeconds =
                            abs(outTime - System.currentTimeMillis()) / 1000
                        LogUtils.w(
                            "lastSeconds--",
                            "${lastSeconds}---${abs(((entity.orderTimeOut ?: (0 - System.currentTimeMillis()))))}"
                        )
                        mViewModel.downTimer(lastSeconds)
                        setPublicOrderInfo(entity)
                        UICompat.setText(
                            mViewBinding.atvWayTitle,
                            DataCompat.getResString(com.sbnh.comm.R.string.pay_way)
                        )
                        mViewBinding.clWayWaitPay.visibility = View.VISIBLE
                        mViewBinding.clWayOther.visibility = View.GONE
                        mViewBinding.atvCancelOrder.visibility = View.VISIBLE
                        UICompat.setText(
                            mViewBinding.includedWaitPay.atvPrice, com.sbnh.comm.R.string.pay_money,
                            "${entity.coin}"
                        )
                        MetaViewCompat.setClickViewEnable(
                            mViewBinding.includedWaitPay.atvSure,
                            true
                        )
                        UICompat.setText(
                            mViewBinding.includedWaitPay.atvSure,
                            com.sbnh.comm.R.string.pay_now
                        )
                        mViewBinding.atvContinueBuy.visibility = View.GONE
                        mViewBinding.atvBackCenter.visibility = View.GONE
                    }
                    STATUS_COMPLETE -> {
                        mViewBinding.clContent.visibility = View.VISIBLE
                        mViewBinding.clWayOther.visibility = View.VISIBLE
                        mViewBinding.clWayWaitPay.visibility = View.GONE
                        GlideCompat.loadWarpImage(
                            com.sbnh.comm.R.mipmap.icon_order_status_complete_pay,
                            mViewBinding.aivStatusOtherContent
                        )
                        UICompat.setText(
                            mViewBinding.atvStatusOtherTitle,
                            com.sbnh.comm.R.string.account_paid
                        )
                        UICompat.setText(
                            mViewBinding.atvStatusOtherDesc,
                            com.sbnh.comm.R.string.good_shave_been_released
                        )
                        setPublicOrderInfo(entity)
                        mViewBinding.clWayWaitPay.visibility = View.GONE
                        mViewBinding.clWayOther.visibility = View.VISIBLE
                        UICompat.setText(
                            mViewBinding.atvBuyModelContent,
                            com.sbnh.comm.R.string.bank_card_payment
                        )
                        mViewBinding.atvCancelOrder.visibility = View.GONE
                        mViewBinding.atvContinueBuy.visibility = View.VISIBLE
                        mViewBinding.atvBackCenter.visibility = View.VISIBLE
                    }
                    STATUS_CANCEL -> {
                        mViewBinding.clContent.visibility = View.VISIBLE
                        mViewBinding.clWayOther.visibility = View.VISIBLE
                        mViewBinding.clWayWaitPay.visibility = View.GONE
                        GlideCompat.loadWarpImage(
                            com.sbnh.comm.R.mipmap.icon_order_status_cancel_pay,
                            mViewBinding.aivStatusOtherContent
                        )
                        UICompat.setText(
                            mViewBinding.atvStatusOtherTitle,
                            com.sbnh.comm.R.string.order_has_been_cancel
                        )
                        UICompat.setText(
                            mViewBinding.atvStatusOtherDesc,
                            null
                        )
                        setPublicOrderInfo(entity)
                        mViewBinding.clWayWaitPay.visibility = View.GONE
                        mViewBinding.clWayOther.visibility = View.VISIBLE
                        UICompat.setText(
                            mViewBinding.atvBuyModelContent,
                            com.sbnh.comm.R.string.cancelled
                        )
                        mViewBinding.atvCancelOrder.visibility = View.GONE
                        mViewBinding.atvContinueBuy.visibility = View.VISIBLE
                        mViewBinding.atvBackCenter.visibility = View.VISIBLE
                    }
                    STATUS_PAY_CALLBACK -> {
                        mViewBinding.clContent.visibility = View.VISIBLE
                        mViewBinding.clWayOther.visibility = View.VISIBLE
                        mViewBinding.clWayWaitPay.visibility = View.GONE
                        GlideCompat.loadWarpImage(
                            com.sbnh.comm.R.mipmap.icon_order_status_proceed,
                            mViewBinding.aivStatusOtherContent
                        )
                        UICompat.setText(
                            mViewBinding.atvStatusOtherTitle,
                            com.sbnh.comm.R.string.on_the_march
                        )
                        UICompat.setText(
                            mViewBinding.atvStatusOtherDesc,
                            null
                        )
                        setPublicOrderInfo(entity)
                        mViewBinding.clWayWaitPay.visibility = View.GONE
                        mViewBinding.clWayOther.visibility = View.VISIBLE
                        UICompat.setText(
                            mViewBinding.atvBuyModelContent,
                            com.sbnh.comm.R.string.on_the_march
                        )
                        mViewBinding.atvCancelOrder.visibility = View.GONE
                        mViewBinding.atvContinueBuy.visibility = View.VISIBLE
                        mViewBinding.atvBackCenter.visibility = View.VISIBLE
                    }
                    else -> {
                        mViewBinding.clContent.visibility = View.GONE
                    }
                }
            }
        }
        mViewModel.mTimerLiveData.observe(this) {
            if (it.status == STATUS_RUNNING) {
                val lastTimes = TimeCompat.timesToMinuteSecond(it.lastTimeLength)
                UICompat.setText(
                    mViewBinding.atvStatusWaitPayDesc,
                    DataCompat.getResString(
                        com.sbnh.comm.R.string.order_commit_timer_down,
                        lastTimes[0], lastTimes[1]
                    )
                )

            } else {
                mViewModel.queryOrderDetails(mOrderId)
            }
        }
    }

    private fun setPublicOrderInfo(entity: OrderEntity) {
        GlideCompat.loadWarpImage(
            entity.resourceUrl,
            mViewBinding.aivCollectionContent,
            PhoneCompat.dp2px(this, 86f),
            PhoneCompat.dp2px(this, 86f)
        )
        UICompat.setText(
            mViewBinding.atvCollectionNumber,
            DataCompat.getResString(com.sbnh.comm.R.string.order_number_is, entity.orderNo ?: "")
        )
        UICompat.setText(mViewBinding.atvCollectionName, entity.businessName)
        UICompat.setText(
            mViewBinding.atvCollectionToken,
            com.sbnh.comm.R.string.collection_token_is,
            entity.token ?: ""
        )
        UICompat.setText(
            mViewBinding.atvCreateTime,
            com.sbnh.comm.R.string.create_time_is,
            TimeCompat.getTimeFormat(entity.createTime)
        )
        UICompat.setText(
            mViewBinding.atvPayMoney,
            com.sbnh.comm.R.string.pay_money,
            "${entity.coin}"
        )
    }

}