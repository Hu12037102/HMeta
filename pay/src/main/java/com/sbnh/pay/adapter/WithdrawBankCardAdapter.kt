package com.sbnh.pay.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.huxiaobai.adapter.BaseRecyclerAdapter
import com.sbnh.comm.compat.DataCompat
import com.sbnh.comm.compat.UICompat
import com.sbnh.comm.entity.pay.BankCardEntity
import com.sbnh.pay.databinding.ItemWithdrawBankCardViewBinding

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/7/14 13:47
 * 更新时间: 2022/7/14 13:47
 * 描述:
 */
class WithdrawBankCardAdapter(context: Context, data: List<BankCardEntity>) :
    BaseRecyclerAdapter<BankCardEntity>(context, data) {
    class ViewHolder(val viewBinding: ItemWithdrawBankCardViewBinding) :
        RecyclerView.ViewHolder(viewBinding.root)

    override fun onBindChildViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            val entity = mData[position]
          //  val lastCardNumber = DataCompat.toString(entity.cardNum).
            UICompat.setText(holder.viewBinding.atvBankCardName, entity.bankName)

        }
    }

    override fun onCreateChildViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder = ViewHolder(
        ItemWithdrawBankCardViewBinding.inflate(
            LayoutInflater.from(mContext),
            parent,
            false
        )
    )
}