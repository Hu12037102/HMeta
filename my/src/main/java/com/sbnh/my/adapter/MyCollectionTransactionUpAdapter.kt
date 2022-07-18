package com.sbnh.my.adapter

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.huxiaobai.adapter.BaseRecyclerAdapter
import com.sbnh.comm.Contract
import com.sbnh.comm.base.callback.OnRecyclerItemClickListener
import com.sbnh.comm.compat.DataCompat
import com.sbnh.comm.compat.UICompat
import com.sbnh.comm.entity.my.MyCollectionEntity
import com.sbnh.comm.other.glide.GlideCompat
import com.sbnh.comm.weight.click.DelayedClick
import com.sbnh.my.databinding.ItemMyCollectionTransactionUpViewBinding

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/7/18 16:34
 * 更新时间: 2022/7/18 16:34
 * 描述:
 */
class MyCollectionTransactionUpAdapter(
    val context: Context,
    val data: ArrayList<MyCollectionEntity>
) :
    BaseRecyclerAdapter<MyCollectionEntity>(context, data) {
    private var mOnRecyclerItemClickListener: OnRecyclerItemClickListener? = null
    fun setOnRecyclerItemClickListener(onRecyclerItemClickListener: OnRecyclerItemClickListener?) {
        this.mOnRecyclerItemClickListener = onRecyclerItemClickListener
    }


    class ViewHolder(val viewBinding: ItemMyCollectionTransactionUpViewBinding) :
        RecyclerView.ViewHolder(viewBinding.root)

    override fun onBindChildViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            val entity = mData[position]
            GlideCompat.loadImage(entity.resourceUrl, holder.viewBinding.aivContent)
            UICompat.setText(holder.viewBinding.atvName, entity.merchandiseName)
            UICompat.setText(
                holder.viewBinding.atvPrice,
                "￥${DataCompat.getMoneyFormat(entity.price)}"
            )
            UICompat.setText(
                holder.viewBinding.atvStatus,
                DataCompat.getResString(com.sbnh.comm.R.string.out_of_stock)
            )
            holder.viewBinding.atvStatus.setOnClickListener(object : DelayedClick() {
                override fun onDelayedClick(v: View?) {
                    mOnRecyclerItemClickListener?.onClickItem(v, position)
                }

            })
        }
    }

    override fun onCreateChildViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder = ViewHolder(
        ItemMyCollectionTransactionUpViewBinding.inflate(
            LayoutInflater.from(mContext), parent, false
        )
    )

}