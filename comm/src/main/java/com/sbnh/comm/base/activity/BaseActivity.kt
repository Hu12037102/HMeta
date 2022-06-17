package com.sbnh.comm.base.activity

import android.os.Bundle
import androidx.annotation.AnimRes
import androidx.annotation.StringRes
import com.google.permission.activity.PermissionActivity
import com.sbnh.comm.compat.ToastCompat

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/12 20:38
 * 更新时间: 2022/6/12 20:38
 * 描述:
 */
abstract class BaseActivity : PermissionActivity() {
    private var isFirstCreate = true
    protected fun showToast(text: CharSequence) {
        ToastCompat.create().showToast(text)
    }

    protected fun showToast(@StringRes stringRes: Int) {
        ToastCompat.create().showToast(stringRes)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        if (isLoadActivityAnimation())
            overridePendingTransition(
                getInActivityAnimationRes(),
                com.sbnh.comm.R.anim.anim_mormal
            )
        super.onCreate(savedInstanceState)
    }


    override fun finish() {
        super.finish()
        if (isLoadActivityAnimation())
            overridePendingTransition(
                com.sbnh.comm.R.anim.anim_mormal,
                getOutActivityAnimationRes()
            )

    }

    @AnimRes
    protected open fun getInActivityAnimationRes(): Int = com.sbnh.comm.R.anim.anim_right_to_center

    @AnimRes
    protected open fun getOutActivityAnimationRes(): Int = com.sbnh.comm.R.anim.anim_center_to_right
    protected open fun isLoadActivityAnimation() = true
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (isFirstCreate) {
            onWindowFirstFocusChanged(hasFocus)
            isFirstCreate = false
        }
    }

    protected open fun onWindowFirstFocusChanged(hasFocus: Boolean) {

    }
}