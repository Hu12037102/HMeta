package com.sbnh.bazaar.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sbnh.bazaar.databinding.ItemBazaarTabViewBinding
import com.sbnh.comm.Contract
import com.sbnh.comm.base.callback.OnRecyclerItemClickListener
import com.sbnh.comm.compat.CollectionCompat
import com.sbnh.comm.compat.MetaViewCompat
import com.sbnh.comm.compat.PhoneCompat
import com.sbnh.comm.compat.UICompat
import com.sbnh.comm.entity.bazaar.BazaarTabEntity

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/7/12 11:57
 * 更新时间: 2022/7/12 11:57
 * 描述:
 */
class BazaarTabAdapter(private val context: Context, private val data: List<BazaarTabEntity>) :
    RecyclerView.Adapter<BazaarTabAdapter.ViewHolder>() {
    companion object {
        const val SING_MAX_LINE_COUNT = 4
    }

    private var mOnRecyclerItemClickListener: OnRecyclerItemClickListener? = null
    fun setOnRecyclerItemClickListener(onRecyclerItemClickListener: OnRecyclerItemClickListener) {
        this.mOnRecyclerItemClickListener = onRecyclerItemClickListener
    }

    class ViewHolder(val viewBinding: ItemBazaarTabViewBinding, count: Int) :
        RecyclerView.ViewHolder(viewBinding.root) {
        init {
            val context = itemView.context
            if (count <= SING_MAX_LINE_COUNT) {
                MetaViewCompat.setViewSize(
                    itemView,
                    PhoneCompat.screenWidth(context) / count,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            } else {
                MetaViewCompat.setViewSize(
                    itemView,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                itemView.setPadding(
                    PhoneCompat.dp2px(context, 20f),
                    0,
                    PhoneCompat.dp2px(context, 20f),
                    0
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemBazaarTabViewBinding.inflate(LayoutInflater.from(context), parent, false),
        CollectionCompat.getListSize(data)
    )

    fun selectorTab(index: Int) {
        for (entity in data) {
            entity.isSelector = data.indexOf(entity) == index
        }
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entity = data[position]
        UICompat.setText(holder.viewBinding.atvContent, entity.name)
        UICompat.setTextColorRes(
            holder.viewBinding.atvContent,
            if (entity.isSelector) com.sbnh.comm.R.color.colorWhite else com.sbnh.comm.R.color.colorFF9A9A9C
        )
        holder.itemView.setOnClickListener {
            if (!entity.isSelector) {
                mOnRecyclerItemClickListener?.onClickItem(it, position)
            }

        }
    }

    override fun getItemCount(): Int = CollectionCompat.getListSize(data)
}