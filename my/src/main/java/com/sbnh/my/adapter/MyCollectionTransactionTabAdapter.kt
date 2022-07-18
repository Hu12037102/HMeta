package com.sbnh.my.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.sbnh.comm.Contract
import com.sbnh.comm.base.callback.OnRecyclerItemClickListener
import com.sbnh.comm.compat.*
import com.sbnh.comm.entity.base.SelectorTabEntity
import com.sbnh.my.databinding.ItemMyCollectionTransactionTabViewBinding

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/7/18 15:32
 * 更新时间: 2022/7/18 15:32
 * 描述:
 */
class MyCollectionTransactionTabAdapter(
    private val context: Context,
    private val data: List<SelectorTabEntity>
) : RecyclerView.Adapter<MyCollectionTransactionTabAdapter.ViewHolder>() {
    private var mOnRecyclerItemClickListener: OnRecyclerItemClickListener? = null
    fun setOnRecyclerItemClickListener(onRecyclerItemClickListener: OnRecyclerItemClickListener) {
        this.mOnRecyclerItemClickListener = onRecyclerItemClickListener
    }

    class ViewHolder(val viewBinding: ItemMyCollectionTransactionTabViewBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemMyCollectionTransactionTabViewBinding.inflate(
            LayoutInflater.from(context), parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entity = data[position]
        UICompat.setText(holder.viewBinding.atvName, entity.name)
        if (entity.isSelector) {
            holder.viewBinding.viewIndicator.visibility = View.VISIBLE
            ViewCompat.setBackground(holder.viewBinding.viewIndicator,createIndicatorDrawable())
        } else {
            holder.viewBinding.viewIndicator.visibility = View.INVISIBLE
        }
        holder.itemView.setOnClickListener {
            if (!entity.isSelector) {
                selectorTab(position)
                mOnRecyclerItemClickListener?.onClickItem(it, position)
            }

        }


    }

    fun selectorTab(index: Int) {
        for (entity in data) {
            entity.isSelector = data.indexOf(entity) == index
        }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = CollectionCompat.getListSize(data)
    private fun createIndicatorDrawable(): Drawable {
        val drawable = GradientDrawableCompat.create()
        drawable.orientation = GradientDrawable.Orientation.LEFT_RIGHT
        drawable.cornerRadius = PhoneCompat.dp2px(context, Contract.DP.VALUE_50F).toFloat()
        drawable.colors = intArrayOf(
            MetaViewCompat.getColor(com.sbnh.comm.R.color.colorFFB450E2),
            MetaViewCompat.getColor(com.sbnh.comm.R.color.colorFF505DFF)
        )
        return drawable
    }
}