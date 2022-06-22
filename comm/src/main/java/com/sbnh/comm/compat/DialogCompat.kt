package com.sbnh.comm.compat

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/19 18:29
 * 更新时间: 2022/6/19 18:29
 * 描述:
 */
object DialogCompat {
    private const val TAG = "DialogFragment"

    @JvmStatic
    fun showFragmentDialog(dialog: DialogFragment?, fragmentManager: FragmentManager?=null) {
        fragmentManager?.let {
            dialog?.showNow(it, TAG)
        }


    }
}