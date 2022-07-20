package com.sbnh.my.adapter

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.huxiaobai.adapter.BaseRecyclerAdapter
import com.sbnh.comm.Contract
import com.sbnh.comm.base.callback.OnRecyclerItemClickListener
import com.sbnh.comm.compat.*
import com.sbnh.comm.entity.my.CollectionNumDetailsEntity
import com.sbnh.comm.weight.click.DelayedClick
import com.sbnh.comm.weight.text.SpanTextHelper
import com.sbnh.my.databinding.ItemCollectionNumDetailsListViewBinding

class CollectionNumDetailsListAdapter(context: Context, data: List<CollectionNumDetailsEntity>) :
    BaseRecyclerAdapter<CollectionNumDetailsEntity>(context, data) {
    private var mOnRecyclerItemClickListener: OnRecyclerItemClickListener? = null
    fun setOnRecyclerItemClickListener(onRecyclerItemClickListener: OnRecyclerItemClickListener?) {
        this.mOnRecyclerItemClickListener = onRecyclerItemClickListener
    }

    class ViewHolder(val viewBinding: ItemCollectionNumDetailsListViewBinding) :
        RecyclerView.ViewHolder(viewBinding.root)

    override fun onBindChildViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            val entity = mData[position]
            UICompat.setText(holder.viewBinding.atvTokenId, "#${entity.tokenId} ")
            UICompat.setText(
                holder.viewBinding.atvTime,
                TimeCompat.getTimeFormat(entity.createTime)
            )
            /* holder.viewBinding.atvGive.setOnClickListener {
                 ARoutersActivity.startGiveCollectionActivity(entity.id, entity.merchandiseId)
                 if (mContext is Activity) {
                     (mContext as Activity).finish()
                 }
             }*/
            holder.viewBinding.atvGive.setOnClickListener(object : DelayedClick() {
                override fun onDelayedClick(v: View?) {
                    mOnRecyclerItemClickListener?.onClickItem(v, position)
                }

            })
            when (entity.status) {
                Contract.MyCollectionStatus.NORMAL -> {
                    UICompat.setText(
                        holder.viewBinding.atvGive,
                        com.sbnh.comm.R.string.hit_the_shelf
                    )
                    holder.viewBinding.atvGive.visibility = View.VISIBLE
                }
                Contract.MyCollectionStatus.SALE -> {
                    UICompat.setText(
                        holder.viewBinding.atvGive,
                        com.sbnh.comm.R.string.out_of_stock
                    )
                    holder.viewBinding.atvGive.visibility = View.VISIBLE
                }
                else -> {
                    holder.viewBinding.atvGive.visibility = View.INVISIBLE
                }
            }
            if (NumberCompat.checkDouble(entity.price) <= 0.0) {
                holder.viewBinding.atvPrice.visibility = View.GONE
            } else {
                holder.viewBinding.atvPrice.visibility = View.VISIBLE
                SpanTextHelper.with()
                    .append("￥${DataCompat.getMoneyAutoFormat(entity.price)}")
                    .setColor(MetaViewCompat.getColor(com.sbnh.comm.R.color.colorWhite))
                    .setSize(15, true)
                    .appendLine()
                    .append(DataCompat.getResString(com.sbnh.comm.R.string.selling_price))
                    .setSize(12, true)
                    .setColor(MetaViewCompat.getColor(com.sbnh.comm.R.color.colorFF9A9A9C))
                    .crete(holder.viewBinding.atvPrice)
            }
        }
    }

    override fun onCreateChildViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder = ViewHolder(
        ItemCollectionNumDetailsListViewBinding.inflate(
            LayoutInflater.from(mContext),
            parent,
            false
        )
    )

    fun updateUpCollection(id: String?, price: Double) {
        for (entity in mData) {
            if (TextUtils.equals(id, entity.id)) {
                entity.status = Contract.MyCollectionStatus.SALE
                entity.price = price
            }
        }
        notifyDataSetChanged()
    }

    fun updateDowCollection(tokenId: Long?) {
        for (entity in mData) {
            if (entity.tokenId == tokenId) {
                entity.price = null
                entity.status = Contract.MyCollectionStatus.NORMAL
            }
        }
        notifyDataSetChanged()
    }
}