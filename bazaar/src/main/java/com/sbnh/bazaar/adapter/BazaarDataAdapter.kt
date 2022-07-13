package com.sbnh.bazaar.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import androidx.viewbinding.ViewBindings
import com.huxiaobai.adapter.BaseRecyclerAdapter
import com.sbnh.bazaar.databinding.ItemBazaarDataViewBinding
import com.sbnh.comm.Contract
import com.sbnh.comm.compat.DataCompat
import com.sbnh.comm.compat.MetaViewCompat
import com.sbnh.comm.compat.TimeCompat
import com.sbnh.comm.compat.UICompat
import com.sbnh.comm.entity.bazaar.BazaarDataEntity
import com.sbnh.comm.weight.text.SpanTextHelper

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/7/13 13:58
 * 更新时间: 2022/7/13 13:58
 * 描述:
 */
class BazaarDataAdapter(val context: Context, val data: List<BazaarDataEntity>) :
    BaseRecyclerAdapter<BazaarDataEntity>(context, data) {
    class ViewHolder(val viewBinding: ItemBazaarDataViewBinding) :
        RecyclerView.ViewHolder(viewBinding.root)

    override fun onBindChildViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            val entity = mData[position]
            UICompat.setText(holder.viewBinding.atvNumber, "#${entity.tokenId}")
            when (entity.status) {
                Contract.CollectionStatus.STATUS_SALE -> {
                    holder.viewBinding.atvNumber.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        com.sbnh.comm.R.mipmap.icon_bazzar_sale,
                        0
                    )
                }
                Contract.CollectionStatus.STATUS_RESERVE -> {
                    holder.viewBinding.atvNumber.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        com.sbnh.comm.R.mipmap.icon_bazzar_reserve,
                        0
                    )
                }
                else -> {
                    holder.viewBinding.atvNumber.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                }
            }
            UICompat.setText(
                holder.viewBinding.atvTime,
                TimeCompat.getTimeFormat(entity.updateTime, "yyyy/MM/dd HH:mm")
            )
            SpanTextHelper.with().append("￥${entity.price}")
                .setColor(MetaViewCompat.getColor(com.sbnh.comm.R.color.colorWhite))
                .setSize(15, true)
                .appendLine()
                .append(DataCompat.getResString(com.sbnh.comm.R.string.selling_price))
                .setSize(12, true)
                .setColor(MetaViewCompat.getColor(com.sbnh.comm.R.color.colorFF9A9A9C))
                .crete(holder.viewBinding.atvPrice)
        }
    }

    override fun onCreateChildViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder =
        ViewHolder(ItemBazaarDataViewBinding.inflate(LayoutInflater.from(mContext), parent, false))


}