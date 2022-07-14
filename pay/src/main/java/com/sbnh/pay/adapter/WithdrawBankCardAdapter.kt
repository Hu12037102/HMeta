package com.sbnh.pay.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.huxiaobai.adapter.BaseRecyclerAdapter
import com.sbnh.comm.base.callback.OnRecyclerItemClickListener
import com.sbnh.comm.compat.DataCompat
import com.sbnh.comm.compat.UICompat
import com.sbnh.comm.entity.pay.BankCardEntity
import com.sbnh.comm.other.glide.GlideCompat
import com.sbnh.comm.weight.click.DelayedClick
import com.sbnh.pay.databinding.ItemWithdrawBankCardViewBinding

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/7/14 13:47
 * 更新时间: 2022/7/14 13:47
 * 描述:
 */
class WithdrawBankCardAdapter(context: Context, data: List<BankCardEntity>) :
    BaseRecyclerAdapter<BankCardEntity>(context, data) {
    private var mOnRecyclerItemClickListener: OnRecyclerItemClickListener? = null
    fun setOnRecyclerItemClickListener(onRecyclerItemClickListener: OnRecyclerItemClickListener) {
        this.mOnRecyclerItemClickListener = onRecyclerItemClickListener
    }

    class ViewHolder(val viewBinding: ItemWithdrawBankCardViewBinding) :
        RecyclerView.ViewHolder(viewBinding.root)

    override fun onBindChildViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            val entity = mData[position]
            val lastCardNumber = DataCompat.toString(entity.cardNum)
            val result = if (lastCardNumber.length > 4) {
                lastCardNumber.substring(lastCardNumber.length - 4)
            } else {
                lastCardNumber
            }
            GlideCompat.loadImage(entity.logo, holder.viewBinding.civBankIcon)
            UICompat.setText(holder.viewBinding.atvBankCardName, "${entity.bankName}\t($result)")
            UICompat.setImageRes(
                holder.viewBinding.aivCheck,
                if ((entity.isCheck)) com.sbnh.comm.R.mipmap.icon_comm_check else com.sbnh.comm.R.mipmap.icon_comm_normal
            )
            holder.itemView.setOnClickListener(object : DelayedClick() {
                override fun onDelayedClick(v: View?) {
                    if (!entity.isCheck) {
                        selectorItem(position)
                        mOnRecyclerItemClickListener?.onClickItem(
                            v,
                            position
                        )
                    }
                }

            })
        }
    }

    private fun selectorItem(index: Int) {
        for (entity in mData) {
            entity.isCheck = mData.indexOf(entity) == index
        }
        notifyDataSetChanged()
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