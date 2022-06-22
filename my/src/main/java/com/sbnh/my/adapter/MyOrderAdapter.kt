package com.sbnh.my.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.huxiaobai.adapter.BaseRecyclerAdapter
import com.sbnh.comm.Contract
import com.sbnh.comm.compat.DataCompat
import com.sbnh.comm.compat.PhoneCompat
import com.sbnh.comm.compat.UICompat
import com.sbnh.comm.entity.order.OrderEntity
import com.sbnh.comm.other.glide.GlideCompat
import com.sbnh.my.databinding.ItemMyOrderViewBinding

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/22 11:46
 * 更新时间: 2022/6/22 11:46
 * 描述:
 */
class MyOrderAdapter(private val context: Context, private val data: List<OrderEntity>) :
    BaseRecyclerAdapter<OrderEntity>(context, data) {
    class ViewHolder(val viewBinding: ItemMyOrderViewBinding) :
        RecyclerView.ViewHolder(viewBinding.root)

    override fun onBindChildViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            val entity = data[position]
            UICompat.setText(
                holder.viewBinding.atvNumber,
                com.sbnh.comm.R.string.order_number_is,
                DataCompat.checkNotNull(entity.orderNo)
            )
            GlideCompat.loadWarpImage(
                entity.resourceUrl,
                holder.viewBinding.aivContent,
                PhoneCompat.dp2px(context, Contract.DP.COLLECTION_IMAGE_SMALL_SIZE.toFloat())
            )
            UICompat.setText(holder.viewBinding.atvName, entity.businessName)
            UICompat.setText(
                holder.viewBinding.atvToken,
                com.sbnh.comm.R.string.collection_token_is,
                DataCompat.checkNotNull(entity.token)
            )
            UICompat.setText(
                holder.viewBinding.atvPrice,
                com.sbnh.comm.R.string.pay_money,
                "${entity.coin}"
            )

        }


    }

    override fun onCreateChildViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder =
        ViewHolder(ItemMyOrderViewBinding.inflate(LayoutInflater.from(context), parent, false))
}