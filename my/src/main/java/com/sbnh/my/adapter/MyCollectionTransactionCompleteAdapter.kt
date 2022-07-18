package com.sbnh.my.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.huxiaobai.adapter.BaseRecyclerAdapter
import com.sbnh.comm.base.callback.OnRecyclerItemClickListener
import com.sbnh.comm.compat.DataCompat
import com.sbnh.comm.compat.UICompat
import com.sbnh.comm.entity.my.MyCollectionEntity
import com.sbnh.comm.other.glide.GlideCompat
import com.sbnh.my.databinding.ItemMyCollectionTransactionCompleteBinding

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/7/18 17:43
 * 更新时间: 2022/7/18 17:43
 * 描述:
 */
class MyCollectionTransactionCompleteAdapter(
    val context: Context,
    val data: List<MyCollectionEntity>
) : BaseRecyclerAdapter<MyCollectionEntity>(context, data) {

    class ViewHolder(val viewBinding: ItemMyCollectionTransactionCompleteBinding) :
        RecyclerView.ViewHolder(viewBinding.root)

    override fun onBindChildViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            val entity = mData[position]
            UICompat.setText(holder.viewBinding.atvNumber, "#${entity.tokenId}")
            GlideCompat.loadImage(entity.resourceUrl, holder.viewBinding.aivContent)
            UICompat.setText(holder.viewBinding.atvName, entity.merchandiseName)
            UICompat.setText(
                holder.viewBinding.atvPrice,
                "￥${DataCompat.getMoneyFormat(entity.price)}"
            )
        }
    }

    override fun onCreateChildViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder = ViewHolder(
        ItemMyCollectionTransactionCompleteBinding.inflate(
            LayoutInflater.from(mContext),
            parent,
            false
        )
    )

}