package com.sbnh.healermeta.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sbnh.comm.base.callback.OnRecyclerItemClickListener
import com.sbnh.comm.compat.CollectionCompat
import com.sbnh.comm.compat.PhoneCompat
import com.sbnh.comm.compat.UICompat
import com.sbnh.comm.compat.MetaViewCompat
import com.sbnh.comm.entity.base.SelectorTabEntity
import com.sbnh.comm.other.glide.GlideCompat
import com.sbnh.healermeta.databinding.ItemMianTabViewBinding

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/12 14:56
 * 更新时间: 2022/6/12 14:56
 * 描述:
 */
class MainTabAdapter(private val context: Context, private val data: List<SelectorTabEntity>) :
    RecyclerView.Adapter<MainTabAdapter.ViewHolder>() {
    private var onRecyclerItemClickListener: OnRecyclerItemClickListener? = null
    fun setOnRecyclerItemClickListener(onRecyclerItemClickListener: OnRecyclerItemClickListener) {
        this.onRecyclerItemClickListener = onRecyclerItemClickListener
    }

    class ViewHolder(var mBinding: ItemMianTabViewBinding, itemSize: Int) :
        RecyclerView.ViewHolder(mBinding.root) {
        init {
            val context = mBinding.root.context
            MetaViewCompat.setViewSize(mBinding.root, PhoneCompat.screenWidth(context) / itemSize)
        }
        //  public val mBinding = binding
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entity = data[position]
        UICompat.setText(holder.mBinding.atvContent, entity.name)
        GlideCompat.loadFitCenterImage(
            if (entity.isSelector) entity.selectorRes else entity.normalRes,
            holder.mBinding.aivContent
        )
        if (entity.isSelector) {
            UICompat.setTextColorRes(
                holder.mBinding.atvContent,
                com.sbnh.comm.R.color.colorWhite
            )
            GlideCompat.loadFitCenterImage(
                entity.selectorRes,
                holder.mBinding.aivContent
            )
            // UICompat.setImageRes(holder.mBinding.aivContent,entity.selectorRes)
        } else {
            UICompat.setTextColorRes(
                holder.mBinding.atvContent,
                com.sbnh.comm.R.color.colorFFD8D8D8
            )
            GlideCompat.loadFitCenterImage(
                entity.normalRes,
                holder.mBinding.aivContent
            )
          //   UICompat.setImageRes(holder.mBinding.aivContent,entity.normalRes)
        }
        holder.mBinding.root.setOnClickListener {
            selectorTab(position)
            onRecyclerItemClickListener?.onClickItem(it, position)
        }
    }

     fun selectorTab(index: Int) {
        for (i in data.indices) {
            data[i].isSelector = i == index
        }
        notifyDataSetChanged()

    }

    override fun getItemCount(): Int = CollectionCompat.getListSize(data)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemMianTabViewBinding.inflate(LayoutInflater.from(context), parent, false),
            itemCount
        )

}