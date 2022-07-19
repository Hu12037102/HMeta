package com.sbnh.comm.base.fragment

import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import com.google.permission.fragment.PermissionFragment
import com.sbnh.comm.compat.ToastCompat
import com.scwang.smart.refresh.layout.api.RefreshLayout

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/13 10:36
 * 更新时间: 2022/6/13 10:36
 * 描述:
 */
abstract class BaseFragment : PermissionFragment() {
    private val mDefaultActivityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            onActivityResultCallback(it)
        }

    protected fun showToast(text: CharSequence) {
        ToastCompat.create().showToast(text)
    }

    protected fun showToast(@StringRes stringRes: Int) {
        ToastCompat.create().showToast(stringRes)
    }

    open fun loadSmartData(
        refreshLayout: RefreshLayout? = null,
        isRefresh: Boolean = true
    ) {

    }



    protected open fun onActivityResultCallback(result: ActivityResult) {

    }

    override fun onDestroy() {
        mDefaultActivityResultLauncher.unregister()
        super.onDestroy()
    }

    protected fun startActivityForResult(intent: Intent) {
        try {
            mDefaultActivityResultLauncher.launch(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}