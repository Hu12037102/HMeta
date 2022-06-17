package com.sbnh.comm.dialog

import com.sbnh.comm.base.dialog.BaseCompatDialog
import com.sbnh.comm.databinding.DialogRealNameBinding
import com.sbnh.comm.viewmodel.BaseDialogViewModel

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/17 17:55
 * 更新时间: 2022/6/17 17:55
 * 描述:
 */
class RealNameDialog : BaseCompatDialog<DialogRealNameBinding, BaseDialogViewModel>() {
    override fun getViewBinding(): DialogRealNameBinding =
        DialogRealNameBinding.inflate(layoutInflater)

    override fun getViewModelClass(): Class<BaseDialogViewModel> = BaseDialogViewModel::class.java

    override fun initView() {

    }

    override fun initData() {
    }

    override fun initEvent() {
    }

}