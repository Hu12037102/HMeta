package com.sbnh.pay.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.huxiaobai.adapter.BaseRecyclerAdapter
import com.sbnh.comm.entity.pay.BankCardEntity
import com.sbnh.pay.databinding.ItemBankCardListViewBinding

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/17 10:42
 * 更新时间: 2022/6/17 10:42
 * 描述:
 */
class BankCardListAdapter(context: Context, data: List<BankCardEntity>) :
    BaseRecyclerAdapter<BankCardEntity>(context, data) {

    class ViewHolder(val binding: ItemBankCardListViewBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onBindChildViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val entity = mData[position]
        /*if (holder is ViewHolder) {
            holder.binding
        }*/
    }

    override fun onCreateChildViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder =
        ViewHolder(ItemBankCardListViewBinding.inflate(LayoutInflater.from(mContext), parent, false))
}