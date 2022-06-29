package com.sbnh.my.adapter

import android.content.Context
import android.graphics.Color
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.toSpannable
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
            setContentTextWhite(holder.viewBinding.atvCode)

            UICompat.setText(
                holder.viewBinding.atvName,
                com.sbnh.comm.R.string.format_goods_name,
                DataCompat.checkNotNull(entity.merchandiseName))
            setContentTextWhite(holder.viewBinding.atvName)

            UICompat.setText(
                holder.viewBinding.atvTime,
                com.sbnh.comm.R.string.format_give_time,
                TimeCompat.getTimeFormat(entity.createTime))
            setContentTextWhite(holder.viewBinding.atvTime)

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
            setContentTextWhite(holder.viewBinding.atvGetRecord)

            UICompat.setText(
                holder.viewBinding.atvUserName,
                resIdUser,
                DataCompat.checkNotNull(entity.nickname)
            )
            setContentTextWhite(holder.viewBinding.atvUserName)
        }

    }

    private fun setContentTextWhite(textView: TextView) {
        val spannable = textView.text.toSpannable()
        spannable.setSpan(
            ForegroundColorSpan(Color.WHITE),
            spannable.indexOf("：", 0, true) + 1,
            spannable.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        textView.text = spannable
    }

    override fun onCreateChildViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder =
        ViewHolder(ItemGiveCollectionListViewBinding.inflate(LayoutInflater.from(context), parent, false))
}