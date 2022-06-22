package com.sbnh.my.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sbnh.comm.base.callback.OnRecyclerItemClickListener
import com.sbnh.comm.compat.CollectionCompat
import com.sbnh.comm.compat.PhoneCompat
import com.sbnh.comm.compat.UICompat
import com.sbnh.comm.compat.MetaViewCompat
import com.sbnh.comm.entity.base.TabEntity
import com.sbnh.comm.other.glide.GlideCompat
import com.sbnh.comm.weight.click.CheckLoginClick
import com.sbnh.my.databinding.ItemMyTabViewBinding

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/14 16:19
 * 更新时间: 2022/6/14 16:19
 * 描述:
 */
class MyTabAdapter(
    private val context: Context,
    private val data: List<TabEntity>,
    private val lineCount: Int
) :
    RecyclerView.Adapter<MyTabAdapter.ViewHolder>() {
    private var mOnRecyclerItemClickListener: OnRecyclerItemClickListener? = null
    fun setOnRecyclerItemClickListener(onRecyclerItemClickListener: OnRecyclerItemClickListener) {
        this.mOnRecyclerItemClickListener = onRecyclerItemClickListener

    }

    class ViewHolder(val binding: ItemMyTabViewBinding, lineCount: Int) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            val mItemView: View = binding.root
            MetaViewCompat.setViewSize(
                mItemView,
                PhoneCompat.screenWidth(mItemView.context) / lineCount,
                PhoneCompat.dp2px(itemView.context, 74f)
            )
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemMyTabViewBinding.inflate(LayoutInflater.from(context), parent, false), lineCount
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entity = data[position]
        UICompat.setText(holder.binding.atvContent, entity.text)
        GlideCompat.loadWarpImage(entity.resDrawable, holder.binding.aivContent)
        initHolderEvent(holder, position)
    }

    private fun initHolderEvent(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener(object : CheckLoginClick(data[position].checkLogin) {
            override fun onCheckLoginClick(v: View?) {
                mOnRecyclerItemClickListener?.onClickItem(v, position)
            }
        })
    }

    override fun getItemCount(): Int = CollectionCompat.getListSize(data)


}