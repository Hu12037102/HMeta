package com.sbnh.comm.base.dialog

import androidx.viewbinding.ViewBinding
import com.sbnh.comm.base.viewmodel.BaseViewModel

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/24 15:49
 * 更新时间: 2022/6/24 15:49
 * 描述:
 */
abstract class BaseDataDialog<VB : ViewBinding, VM : BaseViewModel> :
    BaseCompatDialog<VB, VM>() {
    protected var mOnCallbackValues: OnCallbackValues? = null
    fun setOnCallbackValues(onCallbackValues: OnCallbackValues?) {
        this.mOnCallbackValues = onCallbackValues
    }

    interface OnCallbackValues {
        fun onValue(obj: Any)
    }
}