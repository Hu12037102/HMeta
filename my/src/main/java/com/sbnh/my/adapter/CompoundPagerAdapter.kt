package com.sbnh.my.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.huxiaobai.adapter.BaseRecyclerAdapter
import com.sbnh.comm.compat.CollectionCompat
import com.sbnh.comm.compat.DataCompat
import com.sbnh.comm.compat.MetaViewCompat
import com.sbnh.comm.compat.PhoneCompat
import com.sbnh.comm.entity.my.CompoundPagerEntity
import com.sbnh.comm.other.glide.GlideCompat
import com.sbnh.comm.weight.text.SpanTextHelper
import com.sbnh.my.databinding.ItemCompoundPagerViewBinding

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/7/7 15:26
 * 更新时间: 2022/7/7 15:26
 * 描述:
 */
class CompoundPagerAdapter(context: Context, data: List<CompoundPagerEntity.Details>) :
    BaseRecyclerAdapter<CompoundPagerEntity.Details>(context, data) {

    class ViewHolder(val viewBinding: ItemCompoundPagerViewBinding) :
        RecyclerView.ViewHolder(viewBinding.root)

    override fun onBindChildViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            val entity = mData[position]
            GlideCompat.loadWarpImage(
                com.sbnh.comm.R.mipmap.icon_comm_compund_frame,
                holder.viewBinding.aivBackground
            )
            GlideCompat.loadWarpImage(entity.resourceUrl, holder.viewBinding.aivContent)
            SpanTextHelper.with()
                .append(DataCompat.toString(entity.merchandiseName))
                .setColor(MetaViewCompat.getColor(com.sbnh.comm.R.color.colorFFCE5BFC))
                .appendBlank()
                .append("${DataCompat.toString(entity.availableCount)}/${DataCompat.toString(entity.count)}")
                .setColor(MetaViewCompat.getColor(com.sbnh.comm.R.color.colorFF8DEFFF))
                .crete(holder.viewBinding.atvContent)
           val params = holder.itemView.layoutParams
            if (params is ViewGroup.MarginLayoutParams){
                when (position) {
                    0 -> {
                        params.leftMargin = PhoneCompat.dp2px(mContext,12f)
                        params.rightMargin = PhoneCompat.dp2px(mContext,12f)
                    }
                   /* CollectionCompat.getListSize(mData) - 1 -> {
                        params.rightMargin = PhoneCompat.dp2px(mContext,12f)
                    }*/
                    else -> {
                        params.rightMargin = PhoneCompat.dp2px(mContext,12f)
                    }
                }
            }

        }
    }

    override fun onCreateChildViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder = ViewHolder(
        ItemCompoundPagerViewBinding.inflate(
            LayoutInflater.from(mContext),
            parent,
            false
        )
    )
}