package com.sbnh.comm.dialog

import android.view.View
import com.sbnh.comm.base.dialog.BaseCompatDialog
import com.sbnh.comm.base.interfaces.OnDialogItemInfoClickListener
import com.sbnh.comm.compat.UICompat
import com.sbnh.comm.databinding.DialogTitleBinding
import com.sbnh.comm.viewmodel.BaseDialogViewModel
import com.sbnh.comm.weight.click.DelayedClick

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/19 17:09
 * 更新时间: 2022/6/19 17:09
 * 描述:
 */
class TitleDialog(private val title: String = "") :
    BaseCompatDialog<DialogTitleBinding, BaseDialogViewModel>() {

    override fun getViewBinding(): DialogTitleBinding = DialogTitleBinding.inflate(layoutInflater)

    override fun getViewModelClass(): Class<BaseDialogViewModel> = BaseDialogViewModel::class.java

    override fun initView() {
        UICompat.setText(mViewBinding.atvTitle, title)
    }

    override fun initData() {
    }

    override fun initEvent() {
        mViewBinding.atvCancel.setOnClickListener(object : DelayedClick() {
            override fun onDelayedClick(v: View?) {
               mOnDialogItemInfoClickListener?.onClickCancel(v)
            }

        })
        mViewBinding.atvConfirm.setOnClickListener(object :DelayedClick(){
            override fun onDelayedClick(v: View?) {
                mOnDialogItemInfoClickListener?.onClickConfirm(v)
            }

        })
    }


    private var mOnDialogItemInfoClickListener: OnDialogItemInfoClickListener? = null
    fun setOnDialogItemInfoClickListener(onDialogItemInfoClickListener: OnDialogItemInfoClickListener?) {
        this.mOnDialogItemInfoClickListener = onDialogItemInfoClickListener
    }


}