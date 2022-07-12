package com.sbnh.bazaar.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.huxiaobai.adapter.BaseRecyclerAdapter
import com.sbnh.bazaar.databinding.ItemBazaarContentViewBinding
import com.sbnh.comm.compat.UICompat
import com.sbnh.comm.entity.bazaar.BazaarEntity
import com.sbnh.comm.other.glide.GlideCompat

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/7/12 14:15
 * 更新时间: 2022/7/12 14:15
 * 描述:
 */
class BazaarContentAdapter(context: Context, data: List<BazaarEntity>) :
    BaseRecyclerAdapter<BazaarEntity>(context, data) {
    class ViewHolder(val viewBinding: ItemBazaarContentViewBinding) :
        RecyclerView.ViewHolder(viewBinding.root)


    override fun onCreateChildViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder = ViewHolder(
        ItemBazaarContentViewBinding.inflate(
            LayoutInflater.from(mContext),
            parent,
            false
        )
    )

    override fun onBindChildViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            val entity = mData[position]
            GlideCompat.loadWarpImage(entity.resourceUrl, holder.viewBinding.aivContent)
            UICompat.setText(holder.viewBinding.atvContent, entity.merchandiseName)
            GlideCompat.loadImage(entity.header, holder.viewBinding.civHead)
            UICompat.setText(holder.viewBinding.atvName,entity.nickName)
        }
    }
}