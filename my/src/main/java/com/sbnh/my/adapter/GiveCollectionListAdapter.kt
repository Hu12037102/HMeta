package com.sbnh.my.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.huxiaobai.adapter.BaseRecyclerAdapter
import com.sbnh.comm.compat.DataCompat
import com.sbnh.comm.compat.TimeCompat
import com.sbnh.comm.compat.UICompat
import com.sbnh.comm.entity.my.GiveCollectionEntity
import com.sbnh.comm.other.glide.GlideCompat
import com.sbnh.my.databinding.ItemGiveCollectionListViewBinding

class GiveCollectionListAdapter(private val context: Context, private val data: List<GiveCollectionEntity>) :
    BaseRecyclerAdapter<GiveCollectionEntity>(context, data) {
    class ViewHolder(val viewBinding: ItemGiveCollectionListViewBinding) :
        RecyclerView.ViewHolder(viewBinding.root)

    override fun onBindChildViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            val entity = data[position]
            GlideCompat.loadWarpImage(
                entity.resourceUrl,
                holder.viewBinding.aivContent,
            )
            UICompat.setText(
                holder.viewBinding.atvCode,
                com.sbnh.comm.R.string.format_goods_code,
                DataCompat.toString(entity.tokenId)
            )
            UICompat.setText(
                holder.viewBinding.atvName,
                com.sbnh.comm.R.string.format_goods_name,
                DataCompat.checkNotNull(entity.merchandiseName))
            UICompat.setText(
                holder.viewBinding.atvTime,
                com.sbnh.comm.R.string.format_give_time,
                TimeCompat.getTimeFormat(entity.createTime))
            var resIdRecord = com.sbnh.comm.R.string.format_give_record
            var resIdUser = com.sbnh.comm.R.string.format_get_user
            if (entity.gainChannel == 2) {
                resIdRecord = com.sbnh.comm.R.string.format_get_record
                resIdUser = com.sbnh.comm.R.string.format_give_user
            }

            UICompat.setText(
                holder.viewBinding.atvGetRecord,
                resIdRecord,
                DataCompat.checkNotNull(entity.id)
            )
            UICompat.setText(
                holder.viewBinding.atvUserName,
                resIdUser,
                DataCompat.checkNotNull(entity.nickname)
            )
        }

    }

    override fun onCreateChildViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder =
        ViewHolder(ItemGiveCollectionListViewBinding.inflate(LayoutInflater.from(context), parent, false))
}