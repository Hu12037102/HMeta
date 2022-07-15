package com.sbnh.my.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sbnh.comm.base.callback.OnRecyclerItemClickListener
import com.sbnh.comm.compat.CollectionCompat
import com.sbnh.comm.compat.MetaViewCompat
import com.sbnh.comm.compat.PhoneCompat
import com.sbnh.comm.compat.UICompat
import com.sbnh.comm.entity.base.SelectorTabEntity
import com.sbnh.my.databinding.ItemAccountBillTabViewBinding

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/7/15 9:17
 * 更新时间: 2022/7/15 9:17
 * 描述:
 */
class AccountBillTabAdapter(
    private val context: Context,
    private val data: List<SelectorTabEntity>
) : RecyclerView.Adapter<AccountBillTabAdapter.ViewHolder>() {
    private var mOnRecyclerItemClickListener: OnRecyclerItemClickListener? = null
    fun setOnRecyclerItemClickListener(onRecyclerItemClickListener: OnRecyclerItemClickListener?) {
        this.mOnRecyclerItemClickListener = onRecyclerItemClickListener
    }

    class ViewHolder(val viewBinding: ItemAccountBillTabViewBinding, count: Int) :
        RecyclerView.ViewHolder(viewBinding.root) {
        init {
            MetaViewCompat.setViewSize(
                itemView,
                PhoneCompat.screenWidth(itemView.context) / count,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemAccountBillTabViewBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            ), CollectionCompat.getListSize(data)
        )

     fun selectorTab(index: Int) {
        for (entity in data) {
            entity.isSelector = data.indexOf(entity) == index
        }
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entity = data[position]
        if (entity.isSelector) {
            holder.viewBinding.viewIndicator.visibility = View.VISIBLE
            UICompat.setTextColorRes(
                holder.viewBinding.atvContent,
                com.sbnh.comm.R.color.colorWhite
            )
        } else {
            holder.viewBinding.viewIndicator.visibility = View.INVISIBLE
            UICompat.setTextColorRes(
                holder.viewBinding.atvContent,
                com.sbnh.comm.R.color.colorFF9A9A9C
            )
        }
        UICompat.setText(holder.viewBinding.atvContent, entity.name)
        holder.itemView.setOnClickListener {
            if (!entity.isSelector) {
                selectorTab(position)
                mOnRecyclerItemClickListener?.onClickItem(it, position)
            }

        }


    }

    override fun getItemCount(): Int = CollectionCompat.getListSize(data)
}