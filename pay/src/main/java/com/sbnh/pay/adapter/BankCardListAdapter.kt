package com.sbnh.pay.adapter

import android.content.Context
import android.graphics.Color
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.huxiaobai.adapter.BaseRecyclerAdapter
import com.sbnh.comm.compat.DataCompat
import com.sbnh.comm.compat.MetaViewCompat
import com.sbnh.comm.compat.UICompat
import com.sbnh.comm.entity.pay.BankCardEntity
import com.sbnh.comm.other.glide.GlideCompat
import com.sbnh.comm.utils.LogUtils
import com.sbnh.comm.weight.click.DelayedClick
import com.sbnh.comm.weight.text.SpanTextHelper
import com.sbnh.pay.databinding.ItemBankCardListViewBinding

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/17 10:42
 * 更新时间: 2022/6/17 10:42
 * 描述:
 */
class BankCardListAdapter(context: Context, data: ArrayList<BankCardEntity>) :
    BaseRecyclerAdapter<BankCardEntity>(context, data) {
    private var mOnClickMoreViewListener: OnClickMoreViewListener? = null
    fun setOnClickMoreViewListener(onClickMoreViewListener: OnClickMoreViewListener?) {
        this.mOnClickMoreViewListener = onClickMoreViewListener
    }

    class ViewHolder(val binding: ItemBankCardListViewBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onBindChildViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            if (holder is ViewHolder) {
                val entity = mData[position]
                GlideCompat.loadWarpImage(entity.logo, holder.binding.civBankIcon)
                UICompat.setText(holder.binding.atvBankCardName, entity.bankName)
                UICompat.setText(
                    holder.binding.atvBankCardType,
                    DataCompat.getResString(com.sbnh.comm.R.string.deposit_card)
                )
                holder.binding.cardParent.setCardBackgroundColor(
                    if (DataCompat.isEmpty(entity.backgroundColor)) MetaViewCompat.getColor(
                        com.sbnh.comm.R.color.colorFF363639
                    ) else Color.parseColor("#${entity.backgroundColor}")
                )
                val bankCardNumberStart = StringBuffer("")
                val bankCardNumberEnd = StringBuffer("")
                for (i in 0 until DataCompat.getTextLength(entity.cardNum)) {
                    val char = entity.cardNum?.get(i)
                    if ((i + 1) % 4 == 0) {
                        bankCardNumberStart.append("*  ")
                    } else if (i < 16) {
                        bankCardNumberStart.append("*")
                    } else {
                        bankCardNumberEnd.append(char)
                    }
                }
                LogUtils.w("bankCardNumberStart--", "$bankCardNumberStart---$bankCardNumberEnd")
                SpanTextHelper.with().append(bankCardNumberStart)
                    .setColor(MetaViewCompat.getColor(com.sbnh.comm.R.color.colorFFD4D4D4))
                    .setSize(14, true)
                    .append(bankCardNumberEnd)
                    .setSize(16, true)
                    .setColor(MetaViewCompat.getColor(com.sbnh.comm.R.color.colorWhite))
                    .crete(holder.binding.atvBankCardNumber)
                holder.binding.aivMore.setOnClickListener(object : DelayedClick() {
                    override fun onDelayedClick(v: View?) {
                        mOnClickMoreViewListener?.clickMore(v, position)
                    }

                })
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }


    }

    override fun onCreateChildViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder =
        ViewHolder(
            ItemBankCardListViewBinding.inflate(
                LayoutInflater.from(mContext),
                parent,
                false
            )
        )


    interface OnClickMoreViewListener {
        fun clickMore(view: View?, position: Int)
    }
}