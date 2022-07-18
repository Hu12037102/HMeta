package com.sbnh.comm.dialog

import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.view.ViewCompat
import com.alibaba.android.arouter.facade.annotation.Route
import com.sbnh.comm.Contract
import com.sbnh.comm.base.dialog.BaseDataDialog
import com.sbnh.comm.compat.*
import com.sbnh.comm.databinding.DialogSetTransactionBinding
import com.sbnh.comm.entity.request.RequestUpCollectionEntity
import com.sbnh.comm.http.IApiService
import com.sbnh.comm.other.arouter.ARouterConfig
import com.sbnh.comm.other.arouter.ARoutersActivity
import com.sbnh.comm.utils.LogUtils
import com.sbnh.comm.viewmodel.BaseDialogViewModel
import com.sbnh.comm.weight.click.DelayedClick
import com.sbnh.comm.weight.text.SpanTextHelper

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/7/18 10:57
 * 更新时间: 2022/7/18 10:57
 * 描述:
 */
@Route(path = ARouterConfig.Path.Comm.DIALOG_SET_TRANSACTION)
class SetTransactionDialog : BaseDataDialog<DialogSetTransactionBinding, BaseDialogViewModel>() {
    private var isAgreement: Boolean = false
    private var mId: String = ""
    override fun getViewBinding(): DialogSetTransactionBinding =
        DialogSetTransactionBinding.inflate(layoutInflater)

    override fun getViewModelClass(): Class<BaseDialogViewModel> = BaseDialogViewModel::class.java

    override fun initView() {
        SpanTextHelper.with()
            .append(DataCompat.getResString(com.sbnh.comm.R.string.set_transaction_amount))
            .setColor(MetaViewCompat.getColor(com.sbnh.comm.R.color.colorWhite))
            .setSize(15, true)
            .append(DataCompat.getResString(com.sbnh.comm.R.string.yuan))
            .setSize(12, true)
            .crete(mViewBinding.atvTitle)
        SpanTextHelper.with()
            .append(DataCompat.getResString(com.sbnh.comm.R.string.submit_default_consent_agreement))
            .setSize(12, true)
            .setColor(MetaViewCompat.getColor(com.sbnh.comm.R.color.colorWhite))
            .append(DataCompat.getResString(com.sbnh.comm.R.string.healer_meta_business_rules))
            .setSize(12, true)
            .setClick(MetaViewCompat.getColor(com.sbnh.comm.R.color.colorFFA56CFF)) {
                ARoutersActivity.startWebContentActivity(IApiService.H5.USER_AGREEMENT)
            }.crete(mViewBinding.atvAgreement)
        ViewCompat.setBackground(mViewBinding.clContent, createContentBackground())
    }

    override fun initData() {
        mId = arguments?.getString(ARouterConfig.Key.ID) ?: Contract.DEFAULT_STRING_VALUE
    }

    override fun initEvent() {
        mViewBinding.atvCommit.setOnClickListener(object : DelayedClick() {
            override fun onDelayedClick(v: View?) {
                if (MetaViewCompat.textViewTextIsEmpty(mViewBinding.aetPrice)) {
                    showToast(com.sbnh.comm.R.string.please_set_transaction_money)
                    return
                }
                val moneyText = MetaViewCompat.getTextViewText(mViewBinding.aetPrice)
                val money = NumberCompat.string2Double(DataCompat.toString(moneyText))
                if (money < Contract.MIN_SET_TRANSACTION_MONEY || money > Contract.MAX_SET_TRANSACTION_MONEY) {
                    showToast(com.sbnh.comm.R.string.please_transaction_must_greater_than)
                    return
                }
                LogUtils.w("initEvent--", "$money")
                if (!isAgreement) {
                    showToast(com.sbnh.comm.R.string.please_again_healer_meta_rule)
                    return
                }
                mOnCallbackValues?.onValue(money)

              //  mViewModel.upCollection(RequestUpCollectionEntity(money, mId))

            }

        })
        mViewBinding.aivAgreement.setOnClickListener {
            isAgreement = !isAgreement
            UICompat.setImageRes(
                mViewBinding.aivAgreement,
                if (isAgreement) com.sbnh.comm.R.mipmap.icon_comm_check else com.sbnh.comm.R.mipmap.icon_comm_normal
            )
        }
        mViewBinding.viewFinish.setOnClickListener {
            dismiss()
        }
    }

    private fun createContentBackground(): Drawable {
        val drawable = GradientDrawableCompat.create()
        drawable.setColor(MetaViewCompat.getColor(com.sbnh.comm.R.color.colorFF2E2E2E))
        drawable.cornerRadii = floatArrayOf(
            PhoneCompat.dp2px(DataCompat.checkContext(context), Contract.DP.VALUE_12F).toFloat(),
            PhoneCompat.dp2px(DataCompat.checkContext(context), Contract.DP.VALUE_12F).toFloat(),
            PhoneCompat.dp2px(DataCompat.checkContext(context), Contract.DP.VALUE_12F).toFloat(),
            PhoneCompat.dp2px(DataCompat.checkContext(context), Contract.DP.VALUE_12F).toFloat(),
            0f, 0f, 0f, 0f
        )
        return drawable
    }


}