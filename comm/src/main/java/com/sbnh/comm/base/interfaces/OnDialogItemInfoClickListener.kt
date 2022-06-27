package com.sbnh.comm.base.interfaces

import android.view.View

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/19 18:50
 * 更新时间: 2022/6/19 18:50
 * 描述:
 */
interface OnDialogItemInfoClickListener {
    fun onClickConfirm(view: View?)
    fun onClickCancel(view: View?){}
}