package com.sbnh.my.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.huxiaobai.adapter.BaseRecyclerAdapter
import com.sbnh.comm.compat.TimeCompat
import com.sbnh.comm.compat.UICompat
import com.sbnh.comm.entity.my.CompoundDetailedListEntity
import com.sbnh.comm.other.glide.GlideCompat
import com.sbnh.my.databinding.ItemCompoundDetailedListViewBinding

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/7/6 10:10
 * 更新时间: 2022/7/6 10:10
 * 描述:
 */
class CompoundDetailedListAdapter(context: Context, data: List<CompoundDetailedListEntity>) :
    BaseRecyclerAdapter<CompoundDetailedListEntity>(context, data) {
    class ViewHolder(val viewBinding: ItemCompoundDetailedListViewBinding) :
        RecyclerView.ViewHolder(viewBinding.root)


    override fun onCreateChildViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder = ViewHolder(
        ItemCompoundDetailedListViewBinding.inflate(LayoutInflater.from(mContext), parent, false)
    )

    override fun onBindChildViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            val entity = mData[position]
            UICompat.setText(holder.viewBinding.atvName, entity.name)
            UICompat.setText(holder.viewBinding.atvDesc, entity.desc)
            UICompat.setText(
                holder.viewBinding.atvTime,
                "${
                    TimeCompat.getTimeFormat(
                        entity.startTime,
                        "yyyy/MM/dd HH:mm"
                    )
                }—${TimeCompat.getTimeFormat(entity.endTime, "yyyy/MM/dd HH:mm")}"
            )
            GlideCompat.loadImage(entity.resource, holder.viewBinding.aivContent)
            holder.viewBinding.atvTime.setCompoundDrawablesWithIntrinsicBounds(
                com.sbnh.comm.R.mipmap.icon_comm_time_small,
                0,
                0,
                0
            )
        }
    }
}