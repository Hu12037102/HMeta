package com.sbnh.comm.dialog.viewmodel

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sbnh.comm.base.callback.OnRecyclerItemClickListener
import com.sbnh.comm.compat.CollectionCompat
import com.sbnh.comm.compat.UICompat
import com.sbnh.comm.databinding.ItemPaymentPasswordSoftViewBinding
import com.sbnh.comm.entity.pay.PaymentPasswordNumberEntity
import com.sbnh.comm.entity.pay.SOFT_TYPE_DELETE
import com.sbnh.comm.entity.pay.SOFT_TYPE_NUMBER
import com.sbnh.comm.entity.pay.SOFT_TYPE_UNKNOWN

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/23 18:32
 * 更新时间: 2022/6/23 18:32
 * 描述:
 */
class PaymentPasswordSoftAdapter(
    private val context: Context,
    private val data: List<PaymentPasswordNumberEntity>
) : RecyclerView.Adapter<PaymentPasswordSoftAdapter.ViewHolder>() {
    private var mOnRecyclerItemClickListener: OnRecyclerItemClickListener? = null
    fun setOnRecyclerItemClickListener(onRecyclerItemClickListener: OnRecyclerItemClickListener?) {
        this.mOnRecyclerItemClickListener = onRecyclerItemClickListener
    }

    class ViewHolder(val viewBinding: ItemPaymentPasswordSoftViewBinding) :
        RecyclerView.ViewHolder(viewBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemPaymentPasswordSoftViewBinding.inflate(LayoutInflater.from(context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entity = data[position]
        when (entity.type) {
            SOFT_TYPE_NUMBER -> {
                holder.viewBinding.aivContent.visibility = View.GONE
                holder.viewBinding.atvContent.visibility = View.VISIBLE
                UICompat.setText(holder.viewBinding.atvContent, entity.text)
            }
            SOFT_TYPE_DELETE -> {
                holder.viewBinding.aivContent.visibility = View.VISIBLE
                holder.viewBinding.atvContent.visibility = View.GONE
                UICompat.setImageRes(holder.viewBinding.aivContent, entity.resDrawable)
            }
            else -> {
                holder.viewBinding.aivContent.visibility = View.GONE
                holder.viewBinding.atvContent.visibility = View.GONE
            }
        }
        holder.itemView.setOnClickListener {
            mOnRecyclerItemClickListener?.onClickItem(
                it,
                position
            )
        }

    }

    override fun getItemCount(): Int = CollectionCompat.getListSize(data)

}