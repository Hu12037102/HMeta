package com.sbnh.my.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.huxiaobai.adapter.BaseRecyclerAdapter
import com.sbnh.comm.entity.home.CollectionEntity
import com.sbnh.my.databinding.ItemCompoundDetailedListViewBinding

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/7/6 10:10
 * 更新时间: 2022/7/6 10:10
 * 描述:
 */
class CompoundDetailedListAdapter(context: Context, data: List<CollectionEntity>) :
    BaseRecyclerAdapter<CollectionEntity>(context, data) {
    class ViewHolder(val viewBinding: ItemCompoundDetailedListViewBinding) :
        RecyclerView.ViewHolder(viewBinding.root)



    override fun onCreateChildViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder = ViewHolder(
        ItemCompoundDetailedListViewBinding.inflate(LayoutInflater.from(mContext), parent, false)
    )

    override fun onBindChildViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            if (holder is ViewHolder){

            }
    }
}