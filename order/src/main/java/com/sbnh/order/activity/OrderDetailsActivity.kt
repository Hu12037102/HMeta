package com.sbnh.order.activity

import android.os.Handler
import android.os.Looper
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.sbnh.comm.Contract
import com.sbnh.comm.base.activity.BaseCompatActivity
import com.sbnh.comm.base.dialog.BaseDataDialog
import com.sbnh.comm.base.interfaces.OnDialogItemInfoClickListener
import com.sbnh.comm.base.viewmodel.BaseViewModel
import com.sbnh.comm.compat.*
import com.sbnh.comm.dialog.InputMessageCodeDialog
import com.sbnh.comm.dialog.TitleDialog
import com.sbnh.comm.entity.base.BaseEntity
import com.sbnh.comm.entity.base.STATUS_FINISH
import com.sbnh.comm.entity.base.STATUS_RUNNING
import com.sbnh.comm.entity.order.*
import com.sbnh.comm.entity.pay.BankCardEntity
import com.sbnh.comm.entity.request.RequestCancelOrderEntity
import com.sbnh.comm.entity.request.RequestPayOrderAfterEntity
import com.sbnh.comm.entity.request.RequestPayOrderBeforeEntity
import com.sbnh.comm.other.arouter.ARouterConfig
import com.sbnh.comm.other.arouter.ARouters
import com.sbnh.comm.other.arouter.ARoutersActivity
import com.sbnh.comm.other.glide.GlideCompat
import com.sbnh.comm.utils.LogUtils
import com.sbnh.comm.weight.click.DelayedClick
import com.sbnh.comm.weight.text.SpanTextHelper
import com.sbnh.order.databinding.ActivityOrderDetailsBinding
import com.sbnh.order.viewmodel.OrderDetailsViewModel
import com.scwang.smart.refresh.layout.api.RefreshLayout
import kotlinx.coroutines.delay
import java.util.*
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
    private var mBankCardEntity: BankCardEntity? = null
    private var isReloadWaitPayCount = true
    private var mDetailsEntity: OrderEntity? = null
    private var mOrderType: Int = Contract.PutOrderType.OFFICIAL
    override fun getViewBinding(): ActivityOrderDetailsBinding =
        ActivityOrderDetailsBinding.inflate(layoutInflater)

    private val mHandler by lazy { Handler(Looper.getMainLooper()) }

    override fun getViewModelClass(): Class<OrderDetailsViewModel> =
        OrderDetailsViewModel::class.java

    override fun initView() {
        mOrderId = intent.getStringExtra(ARouterConfig.Key.ID) ?: ""
        mOrderType = intent.getIntExtra(ARouterConfig.Key.TYPE, Contract.PutOrderType.OFFICIAL)
        mViewBinding.clContent.visibility = View.GONE
    }

    override fun initData() {
        loadSmartData()
    }

    override fun initEvent() {

    }

    private var mRunnable = Runnable { loadSmartData() }

    override fun loadSmartData(refreshLayout: RefreshLayout?, isRefresh: Boolean) {
        mViewModel.queryOrderDetails(mOrderId)
    }

    override fun initObserve() {
        super.initObserve()
        mViewModel.mOrderDetailsLiveData.observe(this) {
            val data = BaseEntity.getData(it)
            data?.let { entity ->
                loadDetails(entity)
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

            } else if (it.status == STATUS_FINISH) {
                if (isReloadWaitPayCount) {
                    mHandler.postDelayed(mRunnable, 1500)
                    isReloadWaitPayCount = false
                }

                /*  mDetailsEntity?.let { order ->
                      order.status = STATUS_CANCEL
                      loadDetails(order)
                  }*/

            }
        }
        mViewModel.mBeforePayLiveData.observe(this) {

            it?.data?.let { result ->
                val dialog =
                    ARouters.build(ARouterConfig.Path.Comm.DIALOG_INPUT_MESSAGE_CODE).withString(
                        ARouterConfig.Key.CONTENT,
                        DataCompat.toString(mBankCardEntity?.mobile)
                    ).navigation()
                // ARouters.getFragment(ARouterConfig.Path.Comm.DIALOG_INPUT_MESSAGE_CODE)
                if (dialog is InputMessageCodeDialog) {
                    DialogCompat.showFragmentDialog(dialog, supportFragmentManager)
                    dialog.setOnCallbackValues(object : BaseDataDialog.OnCallbackValues {
                        override fun onValue(obj: Any) {
                            if (obj is CharSequence) {
                                mViewModel.payOrderAfter(
                                    RequestPayOrderAfterEntity(
                                        DataCompat.toString(obj),
                                        DataCompat.toString(result.paymentOrderId),
                                        DataCompat.toString(result.requestId)
                                    )
                                )
                            }
                            dialog.dismiss()
                        }
                    })
                }

            }


        }
        mViewModel.mCancelOrderLiveData.observe(this) {
            MetaViewCompat.finishActivity(this)
            showToast(com.sbnh.comm.R.string.cancel_order_succeed)
        }
        mViewModel.mAfterPayLiveData.observe(this) {
            loadSmartData()
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

    private fun loadDetails(entity: OrderEntity) {
        this.mDetailsEntity = entity
        when (entity.status) {
            STATUS_WAIT_PAY -> {
                mViewBinding.clContent.visibility = View.VISIBLE
                mViewBinding.clStatusWaitPay.visibility = View.VISIBLE
                mViewBinding.clStatusOther.visibility = View.GONE
                val outTime = entity.orderTimeOut ?: 0
                if (outTime > System.currentTimeMillis()) {
                    val lastSeconds = (outTime - System.currentTimeMillis()) / 1000
                    mViewModel.downTimer(lastSeconds)
                    LogUtils.w(
                        "lastSeconds--",
                        "${
                            TimeCompat.getTimeFormat(
                                outTime,
                                "yyyy-MM-dd HH:mm:ss"
                            )
                        }--${
                            TimeCompat.getTimeFormat(
                                System.currentTimeMillis(),
                                "yyyy-MM-dd HH:mm:ss"
                            )
                        }"
                    )
                    mViewBinding.clCancelOrder.visibility = View.VISIBLE
                } else {
                    UICompat.setText(
                        mViewBinding.atvStatusWaitPayDesc,
                        DataCompat.getResString(
                            com.sbnh.comm.R.string.order_commit_timer_down,
                            "0", "0"
                        )
                    )
                    mViewBinding.clCancelOrder.visibility = View.GONE
                }
                setPublicOrderInfo(entity)
                UICompat.setText(
                    mViewBinding.atvWayTitle,
                    DataCompat.getResString(com.sbnh.comm.R.string.pay_way)
                )
                mViewBinding.clWayWaitPay.visibility = View.VISIBLE
                mViewBinding.clWayOther.visibility = View.GONE

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
                mViewBinding.includedWaitPay.root.visibility = View.VISIBLE
                UICompat.setImageRes(
                    mViewBinding.aivWayWaitPayCheck,
                    if (DataCompat.isNull(mBankCardEntity)) com.sbnh.comm.R.mipmap.icon_comm_normal
                    else com.sbnh.comm.R.mipmap.icon_comm_check
                )
                mViewBinding.includedWaitPay.atvSure.setOnClickListener(object :
                    DelayedClick() {
                    override fun onDelayedClick(v: View?) {
                        if (DataCompat.isNull(mBankCardEntity)) {
                            showToast(com.sbnh.comm.R.string.please_selector_pay_way)
                            return
                        }
                        mViewModel.payOrderBefore(
                            RequestPayOrderBeforeEntity(
                                mBankCardEntity?.bindId,
                                DataCompat.toString(entity.businessId),
                                DataCompat.toString(entity.id),
                                mOrderType
                            )
                        )
                    }

                })
                mViewBinding.clWayWaitPay.setOnClickListener(object : DelayedClick() {
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
                                        mViewBinding.aivWayWaitPayCheck,
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
                mViewBinding.atvCancelOrder.setOnClickListener(object : DelayedClick() {
                    override fun onDelayedClick(v: View?) {
                        val titleDialog =
                            TitleDialog(DataCompat.getResString(com.sbnh.comm.R.string.are_you_sure_cancel_order))
                        DialogCompat.showFragmentDialog(titleDialog, supportFragmentManager)
                        titleDialog.setOnDialogItemInfoClickListener(object :
                            OnDialogItemInfoClickListener {
                            override fun onClickConfirm(view: View?) {
                                mViewModel.cancelOrder(RequestCancelOrderEntity(entity.id))
                                titleDialog.dismiss()
                            }

                            override fun onClickCancel(view: View?) {
                                titleDialog.dismiss()
                            }

                        })
                    }

                })
            }
            STATUS_COMPLETE -> {
                mViewBinding.clContent.visibility = View.VISIBLE
                mViewBinding.clWayOther.visibility = View.VISIBLE
                mViewBinding.clWayWaitPay.visibility = View.GONE
                mViewBinding.clStatusWaitPay.visibility = View.GONE
                mViewBinding.clStatusOther.visibility = View.VISIBLE
                GlideCompat.loadWarpImage(
                    com.sbnh.comm.R.mipmap.icon_order_status_complete_pay,
                    mViewBinding.aivStatusOtherContent
                )
                UICompat.setText(
                    mViewBinding.atvStatusOtherTitle,
                    com.sbnh.comm.R.string.account_paid
                )
                mViewBinding.atvStatusOtherDesc.visibility = View.VISIBLE
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
                mViewBinding.clCancelOrder.visibility = View.GONE
                mViewBinding.atvContinueBuy.visibility = View.VISIBLE
                mViewBinding.includedWaitPay.root.visibility = View.GONE
                UICompat.setText(
                    mViewBinding.atvWayTitle,
                    DataCompat.getResString(com.sbnh.comm.R.string.buy_details)
                )
                clickContinueBuy(entity)

            }
            STATUS_CANCEL -> {
                mViewBinding.clContent.visibility = View.VISIBLE
                mViewBinding.clWayOther.visibility = View.VISIBLE
                mViewBinding.clWayWaitPay.visibility = View.GONE
                mViewBinding.clStatusWaitPay.visibility = View.GONE
                mViewBinding.clStatusOther.visibility = View.VISIBLE
                GlideCompat.loadWarpImage(
                    com.sbnh.comm.R.mipmap.icon_order_status_cancel_pay,
                    mViewBinding.aivStatusOtherContent
                )
                UICompat.setText(
                    mViewBinding.atvStatusOtherTitle,
                    com.sbnh.comm.R.string.order_has_been_cancel
                )
                mViewBinding.atvStatusOtherDesc.visibility = View.GONE
                setPublicOrderInfo(entity)
                mViewBinding.clWayWaitPay.visibility = View.GONE
                mViewBinding.clWayOther.visibility = View.VISIBLE
                UICompat.setText(
                    mViewBinding.atvBuyModelContent,
                    com.sbnh.comm.R.string.cancelled
                )
                mViewBinding.clCancelOrder.visibility = View.GONE
                mViewBinding.atvContinueBuy.visibility = View.VISIBLE
                mViewBinding.includedWaitPay.root.visibility = View.GONE
                UICompat.setText(
                    mViewBinding.atvWayTitle,
                    DataCompat.getResString(com.sbnh.comm.R.string.buy_details)
                )
                clickContinueBuy(entity)
            }
            STATUS_PAY_CALLBACK -> {
                mViewBinding.clContent.visibility = View.VISIBLE
                mViewBinding.clWayOther.visibility = View.VISIBLE
                mViewBinding.clWayWaitPay.visibility = View.GONE
                mViewBinding.clStatusWaitPay.visibility = View.GONE
                mViewBinding.clStatusOther.visibility = View.VISIBLE
                GlideCompat.loadWarpImage(
                    com.sbnh.comm.R.mipmap.icon_order_status_proceed,
                    mViewBinding.aivStatusOtherContent
                )
                UICompat.setText(
                    mViewBinding.atvStatusOtherTitle,
                    com.sbnh.comm.R.string.on_the_march
                )
                mViewBinding.atvStatusOtherDesc.visibility = View.GONE
                setPublicOrderInfo(entity)
                mViewBinding.clWayWaitPay.visibility = View.GONE
                mViewBinding.clWayOther.visibility = View.VISIBLE
                UICompat.setText(
                    mViewBinding.atvBuyModelContent,
                    com.sbnh.comm.R.string.on_the_march
                )
                mViewBinding.clCancelOrder.visibility = View.GONE
                mViewBinding.atvContinueBuy.visibility = View.VISIBLE
                mViewBinding.includedWaitPay.root.visibility = View.GONE
                UICompat.setText(
                    mViewBinding.atvWayTitle,
                    DataCompat.getResString(com.sbnh.comm.R.string.buy_details)
                )
                clickContinueBuy(entity)
            }
            else -> {
                mViewBinding.clContent.visibility = View.GONE
            }
        }
        //市场购买来的就把继续购买隐藏
        if (mOrderType == Contract.PutOrderType.BAZAAR_BUY) {
            mViewBinding.atvContinueBuy.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mHandler.removeCallbacks(mRunnable)
    }

    private fun clickContinueBuy(entity: OrderEntity) {
        mViewBinding.atvContinueBuy.setOnClickListener(object : DelayedClick() {
            override fun onDelayedClick(v: View?) {
                /*   ARouters.build(ARouterConfig.Path.Home.ACTIVITY_COLLECTION_DETAILS)
                       .withString(ARouterConfig.Key.ID, entity.businessId).navigation()*/
                ARoutersActivity.startCollectionDetailsActivity(entity.businessId)
                MetaViewCompat.finishActivitySetResult(this@OrderDetailsActivity)
            }

        })
    }

}