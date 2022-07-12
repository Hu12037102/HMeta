package com.sbnh.bazaar.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sbnh.bazaar.databinding.ItemBazaarDetailsTabViewBinding
import com.sbnh.comm.base.callback.OnRecyclerItemClickListener
import com.sbnh.comm.compat.CollectionCompat
import com.sbnh.comm.compat.UICompat
import com.sbnh.comm.entity.base.SelectorTabEntity

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/7/12 19:45
 * 更新时间: 2022/7/12 19:45
 * 描述:
 */
class BazaarDetailsTabAdapter(
    private val context: Context,
    private val data: List<SelectorTabEntity>
) : RecyclerView.Adapter<BazaarDetailsTabAdapter.ViewHolder>() {
    private var mOnRecyclerItemClickListener: OnRecyclerItemClickListener? = null
    fun setOnRecyclerItemClickListener(onRecyclerItemClickListener: OnRecyclerItemClickListener?) {
        this.mOnRecyclerItemClickListener = onRecyclerItemClickListener
    }

    class ViewHolder(val viewBinding: ItemBazaarDetailsTabViewBinding) :
        RecyclerView.ViewHolder(viewBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemBazaarDetailsTabViewBinding.inflate(LayoutInflater.from(context), parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entity = data[position]
        UICompat.setText(holder.viewBinding.atvContent, entity.name)
        if (entity.isSelector) {
            holder.viewBinding.viewIndicator.visibility = View.VISIBLE
        } else {
            holder.viewBinding.viewIndicator.visibility = View.INVISIBLE
        }
        UICompat.setTextColorRes(
            holder.viewBinding.atvContent,
            if (entity.isSelector) com.sbnh.comm.R.color.colorWhite else com.sbnh.comm.R.color.colorFF9A9A9C
        )
        holder.itemView.setOnClickListener {
            if (!entity.isSelector){
                selectorTab(position)
                mOnRecyclerItemClickListener?.onClickItem(it, position)
            }

        }
    }
    private fun selectorTab(index:Int){
        for (entity in data){
            entity.isSelector = data.indexOf(entity) == index
        }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = CollectionCompat.getListSize(data)
}