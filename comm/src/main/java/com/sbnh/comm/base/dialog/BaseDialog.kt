package com.sbnh.comm.base.dialog

import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatDialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sbnh.comm.compat.ToastCompat

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/17 17:46
 * 更新时间: 2022/6/17 17:46
 * 描述:
 */
open class BaseDialog :AppCompatDialogFragment() {

    protected fun showToast(text: CharSequence) {
        ToastCompat.create().showToast(text)
    }

    protected fun showToast(@StringRes stringRes: Int) {
        ToastCompat.create().showToast(stringRes)
    }
}