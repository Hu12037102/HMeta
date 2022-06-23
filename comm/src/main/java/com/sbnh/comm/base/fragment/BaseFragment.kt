package com.sbnh.comm.base.fragment

import androidx.annotation.StringRes
import androidx.viewbinding.ViewBinding
import com.google.permission.fragment.PermissionFragment
import com.sbnh.comm.Contract
import com.sbnh.comm.base.viewmodel.BaseViewModel
import com.sbnh.comm.compat.ToastCompat
import com.scwang.smart.refresh.layout.api.RefreshLayout

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/13 10:36
 * 更新时间: 2022/6/13 10:36
 * 描述:
 */
abstract class BaseFragment : PermissionFragment() {

    protected fun showToast(text: CharSequence) {
        ToastCompat.create().showToast(text)
    }

    protected fun showToast(@StringRes stringRes: Int) {
        ToastCompat.create().showToast(stringRes)
    }

    protected open fun onLoadSmartData(
        refreshLayout: RefreshLayout? = null,
        isRefresh: Boolean = true) {

    }
}