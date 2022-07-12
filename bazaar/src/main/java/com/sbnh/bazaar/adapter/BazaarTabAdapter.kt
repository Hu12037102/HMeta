package com.sbnh.bazaar.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sbnh.bazaar.databinding.ItemBazaarTabViewBinding
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
    private var mOnRecyclerItemClickListener: OnRecyclerItemClickListener? = null
    fun setOnRecyclerItemClickListener(onRecyclerItemClickListener: OnRecyclerItemClickListener) {
        this.mOnRecyclerItemClickListener = onRecyclerItemClickListener
    }

    class ViewHolder(val viewBinding: ItemBazaarTabViewBinding, count: Int) :
        RecyclerView.ViewHolder(viewBinding.root) {
        init {
            val context = itemView.context
            MetaViewCompat.setViewSize(
                itemView,
                PhoneCompat.screenWidth(context) / count,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemBazaarTabViewBinding.inflate(LayoutInflater.from(context), parent, false),
        CollectionCompat.getListSize(data)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entity = data[position]
        UICompat.setText(holder.viewBinding.atvContent, entity.name)
        holder.itemView.setOnClickListener {
            mOnRecyclerItemClickListener?.onClickItem(it, position)
        }
    }

    override fun getItemCount(): Int = CollectionCompat.getListSize(data)
}