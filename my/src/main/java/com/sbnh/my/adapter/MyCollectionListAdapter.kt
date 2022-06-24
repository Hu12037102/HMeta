package com.sbnh.my.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.huxiaobai.adapter.BaseRecyclerAdapter
import com.sbnh.comm.compat.UICompat
import com.sbnh.comm.entity.home.CollectionEntity
import com.sbnh.comm.entity.my.MyCollectionEntity
import com.sbnh.comm.other.glide.GlideCompat
import com.sbnh.my.databinding.ItemMyCollectionListViewBinding

class MyCollectionListAdapter(context: Context, data: List<MyCollectionEntity>) :
    BaseRecyclerAdapter<MyCollectionEntity>(context, data) {
    class ViewHolder(val viewBinding: ItemMyCollectionListViewBinding) :
        RecyclerView.ViewHolder(viewBinding.root)

    override fun onBindChildViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            val entity = mData[position]
            GlideCompat.loadWarpImage(entity.resourceUrl, holder.viewBinding.aivContent)
            UICompat.setText(holder.viewBinding.atvCollectionName, entity.merchandiseName)
            UICompat.setText(holder.viewBinding.atvCount, "共${entity.count}个")
        }
    }

    override fun onCreateChildViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder = ViewHolder(
        ItemMyCollectionListViewBinding.inflate(
            LayoutInflater.from(mContext),
            parent,
            false
        )
    )
}