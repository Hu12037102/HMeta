package com.sbnh.comm.dialog

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import com.sbnh.comm.Contract
import com.sbnh.comm.base.callback.OnRecyclerItemClickListener
import com.sbnh.comm.base.dialog.BaseCompatDialog
import com.sbnh.comm.compat.DataCompat
import com.sbnh.comm.compat.MetaViewCompat
import com.sbnh.comm.compat.UICompat
import com.sbnh.comm.databinding.DialogSetPaymentPasswordViewBinding
import com.sbnh.comm.dialog.viewmodel.DialogSetPaymentPasswordViewModel
import com.sbnh.comm.dialog.viewmodel.PaymentPasswordSoftAdapter
import com.sbnh.comm.entity.pay.PaymentPasswordNumberEntity
import com.sbnh.comm.entity.pay.SOFT_TYPE_DELETE
import com.sbnh.comm.entity.pay.SOFT_TYPE_NUMBER

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/23 17:34
 * 更新时间: 2022/6/23 17:34
 * 描述:
 */
class SetPaymentPasswordDialog(private val title: String = "", private val desc: String = "") :
    BaseCompatDialog<DialogSetPaymentPasswordViewBinding, DialogSetPaymentPasswordViewModel>() {
    private var mSoftAdapter: PaymentPasswordSoftAdapter? = null
    private val mSoftData = ArrayList<PaymentPasswordNumberEntity>()
    private var mOnInputPasswordCallback: OnInputPasswordCallback? = null
    fun setOnInputPasswordCallback(onInputPasswordCallback: OnInputPasswordCallback?) {
        this.mOnInputPasswordCallback = onInputPasswordCallback
    }

    override fun getViewBinding(): DialogSetPaymentPasswordViewBinding =
        DialogSetPaymentPasswordViewBinding.inflate(layoutInflater)

    override fun getViewModelClass(): Class<DialogSetPaymentPasswordViewModel> =
        DialogSetPaymentPasswordViewModel::class.java

    override fun initView() {
        mViewBinding.rvSoft.layoutManager = GridLayoutManager(context, 3)
        UICompat.setText(mViewBinding.atvTitle, title)
        UICompat.setText(mViewBinding.atvDesc, desc)
    }

    override fun initData() {
        mSoftData.addAll(mViewModel.getPasswordNumberData())
        mSoftAdapter = PaymentPasswordSoftAdapter(requireContext(), mSoftData)
        mViewBinding.rvSoft.adapter = mSoftAdapter
    }

    override fun initEvent() {
        mSoftAdapter?.setOnRecyclerItemClickListener(object : OnRecyclerItemClickListener {
            override fun onClickItem(view: View?, position: Int) {
                val entity = mSoftData[position]
                if (entity.type == SOFT_TYPE_NUMBER) {
                    setPassword(entity.text)
                    val password = getPassword()
                    if (DataCompat.getTextLength(password) == Contract.PAY_PASSWORD_LENGTH) {
                        mOnInputPasswordCallback?.onComplete(password)
                    }
                } else if (entity.type == SOFT_TYPE_DELETE) {
                    deletePassword()
                }

            }

        })
    }


    private fun deletePassword() {
        val parentView = mViewBinding.clPasswordContent
        for (i in parentView.childCount - 1 downTo 0) {
            val childView = parentView.getChildAt(i)
            if (childView is TextView && !MetaViewCompat.textViewTextIsEmpty(childView)) {
                UICompat.setText(childView, null)
                break
            }
        }
    }

    private fun setPassword(text: CharSequence?) {
        val parentView = mViewBinding.clPasswordContent
        for (i in 0 until parentView.childCount) {
            val childView = parentView.getChildAt(i)
            if (childView is TextView && MetaViewCompat.textViewTextIsEmpty(childView)) {
                UICompat.setText(childView, text)
                break
            }
        }
    }

    private fun getPassword(): String {
        val parentView = mViewBinding.clPasswordContent
        var password = ""
        for (i in 0 until parentView.childCount) {
            val childView = parentView.getChildAt(i)
            if (childView is TextView && !MetaViewCompat.textViewTextIsEmpty(childView)) {
                password += MetaViewCompat.getTextViewText(childView)
            }
        }
        return password
    }

    interface OnInputPasswordCallback {
        fun onComplete(password: String)
    }
}