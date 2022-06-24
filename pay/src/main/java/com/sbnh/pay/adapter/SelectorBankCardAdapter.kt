package com.sbnh.pay.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.huxiaobai.adapter.BaseRecyclerAdapter
import com.sbnh.comm.base.callback.OnRecyclerItemClickListener
import com.sbnh.comm.compat.UICompat
import com.sbnh.comm.entity.pay.BankCardEntity
import com.sbnh.comm.other.glide.GlideCompat
import com.sbnh.comm.utils.LogUtils
import com.sbnh.pay.databinding.ItemSelectorBankCardViewBinding

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/24 11:42
 * 更新时间: 2022/6/24 11:42
 * 描述:
 */
class SelectorBankCardAdapter(
    context: Context,
    data: List<BankCardEntity>
) : BaseRecyclerAdapter<BankCardEntity>(context, data) {
    private var mOnRecyclerItemClickListener:OnRecyclerItemClickListener?=null
    fun setOnRecyclerItemClickListener(onRecyclerItemClickListener:OnRecyclerItemClickListener?){
        this.mOnRecyclerItemClickListener= onRecyclerItemClickListener
    }
    class ViewHolder(val viewBinding: ItemSelectorBankCardViewBinding) :
        RecyclerView.ViewHolder(viewBinding.root)

    override fun onBindChildViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            val entity = mData[position]
            GlideCompat.loadImage(entity.logo, holder.viewBinding.aivBankIcon)
            UICompat.setText(holder.viewBinding.atvContent, entity.bankName)
            UICompat.setImageRes(
                holder.viewBinding.aivCheck,
                if (entity.isCheck) com.sbnh.comm.R.mipmap.icon_comm_check else com.sbnh.comm.R.mipmap.icon_comm_normal
            )
            holder.itemView.setOnClickListener{
                if (!entity.isCheck){
                    selectorIndex(position)
                    mOnRecyclerItemClickListener?.onClickItem(it,position)
                }

            }

        }

    }

   private fun selectorIndex(index: Int) {
        for (entity in mData) {
            entity.isCheck = mData.indexOf(entity) == index
        }
        notifyDataSetChanged()
    }


    override fun onCreateChildViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder = ViewHolder(
        ItemSelectorBankCardViewBinding.inflate(LayoutInflater.from(mContext), parent, false)
    )
}