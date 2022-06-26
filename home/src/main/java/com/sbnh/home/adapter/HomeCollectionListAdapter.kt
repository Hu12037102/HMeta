package com.sbnh.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.huxiaobai.adapter.BaseRecyclerAdapter
import com.sbnh.comm.base.callback.OnRecyclerItemClickListener
import com.sbnh.comm.compat.DataCompat
import com.sbnh.comm.compat.TimeCompat
import com.sbnh.comm.compat.UICompat
import com.sbnh.comm.entity.home.CollectionEntity
import com.sbnh.comm.entity.home.STATUS_OUT
import com.sbnh.comm.other.glide.GlideCompat
import com.sbnh.comm.weight.click.CheckLoginClick
import com.sbnh.home.databinding.ItemHomeCollectionListViewBinding

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/20 16:01
 * 更新时间: 2022/6/20 16:01
 * 描述:
 */
class HomeCollectionListAdapter(context: Context, data: List<CollectionEntity>) :
    BaseRecyclerAdapter<CollectionEntity>(context, data) {
    private var mOnRecyclerItemClickListener: OnRecyclerItemClickListener? = null
    fun setOnRecyclerItemClickListener(onRecyclerItemClickListener: OnRecyclerItemClickListener?) {
        this.mOnRecyclerItemClickListener = onRecyclerItemClickListener
    }

    class ViewHolder(val viewBinding: ItemHomeCollectionListViewBinding) :
        RecyclerView.ViewHolder(viewBinding.root)

    override fun onBindChildViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            val entity = mData[position]
            if (CollectionEntity.hasSaleTime(entity)) {
                holder.viewBinding.clBuyTime.visibility = View.VISIBLE
                UICompat.setText(
                    holder.viewBinding.atvBuyTime,
                    "${DataCompat.getResString(com.sbnh.comm.R.string.sale)}${
                        TimeCompat.getSaleTimeFormat(
                            entity.saleTime
                        )
                    }"
                )
            } else {
                holder.viewBinding.clBuyTime.visibility = View.GONE
            }
            GlideCompat.loadWarpImage(entity.resourceUrl, holder.viewBinding.aivContent)
            UICompat.setText(holder.viewBinding.atvCollectionName, entity.merchandiseName)
            GlideCompat.loadImage(entity.header, holder.viewBinding.civHead)
            UICompat.setText(holder.viewBinding.atvUserName, entity.nickname)
            UICompat.setText(holder.viewBinding.atvPrice, "￥${entity.price}")
            UICompat.setText(
                holder.viewBinding.includedLimit.atvLimitCount,
                "${entity.remainQuantity}/${entity.totalQuantity}"
            )
            holder.itemView.setOnClickListener(object : CheckLoginClick() {
                override fun onCheckLoginClick(v: View?) {
                    mOnRecyclerItemClickListener?.onClickItem(v, position)
                }

            })
            holder.viewBinding.aivOrderStatus.visibility =
                if (entity.saleStatus == STATUS_OUT) View.VISIBLE else View.GONE
        }

    }

    override fun onCreateChildViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder = ViewHolder(
        ItemHomeCollectionListViewBinding.inflate(
            LayoutInflater.from(mContext),
            parent,
            false
        )
    )
}