package com.sbnh.my.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.sbnh.comm.Contract
import com.sbnh.comm.base.callback.OnRecyclerItemClickListener
import com.sbnh.comm.compat.*
import com.sbnh.comm.entity.base.TabEntity
import com.sbnh.comm.entity.order.OrderEntity
import com.sbnh.comm.entity.order.OrderTabEntity
import com.sbnh.my.databinding.ItemMyOrderTabViewBinding
import com.sbnh.my.databinding.ItemMyTabViewBinding

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/21 20:23
 * 更新时间: 2022/6/21 20:23
 * 描述:
 */
class MyOrderTabAdapter(private val context: Context, private val data: List<OrderTabEntity>) :
    RecyclerView.Adapter<MyOrderTabAdapter.ViewHolder>() {
    private var mOnRecyclerItemClickListener: OnRecyclerItemClickListener? = null
    fun setOnRecyclerItemClickListener(onRecyclerItemClickListener: OnRecyclerItemClickListener) {
        this.mOnRecyclerItemClickListener = onRecyclerItemClickListener

    }

    class ViewHolder(val viewBinding: ItemMyOrderTabViewBinding, count: Int) :
        RecyclerView.ViewHolder(viewBinding.root) {
        init {
            MetaViewCompat.setViewSize(
                itemView,
                PhoneCompat.screenWidth(viewBinding.root.context) / count,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemMyOrderTabViewBinding.inflate(LayoutInflater.from(context), parent, false),
        CollectionCompat.getListSize(data)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entity = data[position]
        UICompat.setText(holder.viewBinding.atvName, entity.name)
        if (entity.isSelector) {
            ViewCompat.setBackground(holder.viewBinding.viewIndicator, createIndicatorDrawable())
            UICompat.setTextColor(
                holder.viewBinding.atvName,
                MetaViewCompat.getColor(com.sbnh.comm.R.color.white)
            )
        } else {
            ViewCompat.setBackground(holder.viewBinding.viewIndicator, null)
            UICompat.setTextColor(
                holder.viewBinding.atvName,
                MetaViewCompat.getColor(com.sbnh.comm.R.color.colorFF9A9A9C)
            )
        }
        holder.itemView.setOnClickListener {
            selectorTab(position)
            mOnRecyclerItemClickListener?.onClickItem(it, position)
        }

    }

    private fun selectorTab(index: Int) {
        for (i in data.indices) {
            val entity = data[i]
            entity.isSelector = i == index
        }
        notifyDataSetChanged()
    }

    private fun createIndicatorDrawable(): Drawable {
        val drawable = GradientDrawableCompat.create()
        drawable.orientation = GradientDrawable.Orientation.LEFT_RIGHT
        drawable.cornerRadius = PhoneCompat.dp2px(context, Contract.DP.VALUE_50F).toFloat()
        drawable.colors = intArrayOf(
            MetaViewCompat.getColor(com.sbnh.comm.R.color.colorFFB857D5),
            MetaViewCompat.getColor(com.sbnh.comm.R.color.colorFF4724BA)
        )
        return drawable
    }

    override fun getItemCount(): Int = CollectionCompat.getListSize(data)
}