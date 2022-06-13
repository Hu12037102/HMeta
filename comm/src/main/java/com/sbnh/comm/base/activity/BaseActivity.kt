package com.sbnh.comm.base.activity

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.google.permission.activity.PermissionActivity
import com.sbnh.comm.compat.ToastCompat

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/12 20:38
 * 更新时间: 2022/6/12 20:38
 * 描述:
 */
abstract class BaseActivity : PermissionActivity() {
    protected fun showToast(text: CharSequence) {
        ToastCompat.create().showToast(text)
    }

    protected fun showToast(@StringRes stringRes: Int) {
        ToastCompat.create().showToast(stringRes)
    }
}