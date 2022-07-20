package com.sbnh.my.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.huxiaobai.adapter.BaseRecyclerAdapter
import com.sbnh.comm.Contract
import com.sbnh.comm.compat.*
import com.sbnh.comm.entity.base.SelectorTabEntity
import com.sbnh.comm.entity.my.AccountBillEntity
import com.sbnh.comm.other.glide.GlideCompat
import com.sbnh.my.databinding.ItemAccountBilContentViewBinding

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/7/15 10:16
 * 更新时间: 2022/7/15 10:16
 * 描述:
 */
class AccountBillContentAdapter(context: Context, data: List<AccountBillEntity>) :
    BaseRecyclerAdapter<AccountBillEntity>(context, data) {
    class ViewHolder(val viewBinding: ItemAccountBilContentViewBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {}

    override fun onBindChildViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            val entity = mData[position]
            GlideCompat.loadRoundWarpImage(
                entity.resource,
                holder.viewBinding.aivContent,
                PhoneCompat.dp2px(mContext, Contract.DP.VALUE_8F)
            )
            when (entity.targetType) {
                //充值
                SelectorTabEntity.AccountBill.TYPE_TOP_UP -> {
                    UICompat.setText(
                        holder.viewBinding.atvTitle,
                        DataCompat.getResString(com.sbnh.comm.R.string.top_up)
                    )
                    setMoneyText(
                        holder.viewBinding.atvMoney,
                        true,
                        NumberCompat.checkDouble(entity.amount)
                    )
                }
                //提现
                SelectorTabEntity.AccountBill.TYPE_WITHDRAW -> {
                    UICompat.setText(
                        holder.viewBinding.atvTitle,
                        DataCompat.getResString(com.sbnh.comm.R.string.withdraw)
                    )
                    setMoneyText(
                        holder.viewBinding.atvMoney,
                        false,
                        NumberCompat.checkDouble(entity.amount)
                    )
                }
                //支出
                SelectorTabEntity.AccountBill.TYPE_EXPEND -> {
                    UICompat.setText(
                        holder.viewBinding.atvTitle,
                        DataCompat.getResString(com.sbnh.comm.R.string.buy_shop)
                    )
                    setMoneyText(
                        holder.viewBinding.atvMoney,
                        false,
                        NumberCompat.checkDouble(entity.amount)
                    )
                }
                //收入
                SelectorTabEntity.AccountBill.TYPE_REVENUES -> {
                    UICompat.setText(
                        holder.viewBinding.atvTitle,
                        DataCompat.getResString(com.sbnh.comm.R.string.selling_market)
                    )
                    setMoneyText(
                        holder.viewBinding.atvMoney,
                        true,
                        NumberCompat.checkDouble(entity.amount)
                    )
                }
                else -> {
                    setMoneyText(
                        holder.viewBinding.atvMoney,
                        false,
                        NumberCompat.checkDouble(entity.amount)
                    )
                }
            }
            UICompat.setText(holder.viewBinding.atvDesc, entity.desc)
            UICompat.setText(
                holder.viewBinding.atvTime,
                TimeCompat.getTimeFormat(entity.createTime, "yyyy-MM-dd HH:mm:ss")
            )
        }

    }

    /**
     * 是不是正收益
     */
    private fun setMoneyText(textView: TextView, isPositiveNumber: Boolean, moneyNumber: Double) {

        UICompat.setTextColorRes(
            textView,
            if (isPositiveNumber) com.sbnh.comm.R.color.colorFFF4CD9A else com.sbnh.comm.R.color.colorWhite
        )
      //  val moneyFormat = DataCompat.getBalanceFormat("$moneyNumber")
        val moneyFormat = DataCompat.getMoneyFormat(moneyNumber)

        UICompat.setText(
            textView,
            if (isPositiveNumber)
                "+$moneyFormat"
            else
                "-$moneyFormat"
        )

    }

    override fun onCreateChildViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder = ViewHolder(
        ItemAccountBilContentViewBinding.inflate(
            LayoutInflater.from(mContext),
            parent,
            false
        )
    )
}