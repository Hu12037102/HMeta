package com.sbnh.my.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.huxiaobai.adapter.BaseRecyclerAdapter
import com.sbnh.comm.compat.TimeCompat
import com.sbnh.comm.compat.UICompat
import com.sbnh.comm.entity.my.CollectionNumDetailsEntity
import com.sbnh.my.databinding.ItemCollectionNumDetailsListViewBinding

class CollectionNumDetailsListAdapter(context: Context, data: List<CollectionNumDetailsEntity>) :
    BaseRecyclerAdapter<CollectionNumDetailsEntity>(context, data) {
    class ViewHolder(val viewBinding: ItemCollectionNumDetailsListViewBinding) :
        RecyclerView.ViewHolder(viewBinding.root)

    override fun onBindChildViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            val entity = mData[position]
            UICompat.setText(holder.viewBinding.atvTokenId, "#${entity.tokenId} ")
            UICompat.setText(holder.viewBinding.atvTime, TimeCompat.getTimeFormat(entity.createTime))
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
}