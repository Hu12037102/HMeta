package com.sbnh.comm.compat

import android.widget.Toast
import androidx.annotation.StringRes
import com.sbnh.comm.app.BaseApplication

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/10 14:38
 * 更新时间: 2022/6/10 14:38
 * 描述:
 */
class ToastCompat private constructor() {
    private var mToast: Toast? = null

    companion object {
        private val mInstance: ToastCompat by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            ToastCompat()
        }

        @JvmStatic
        fun create(): ToastCompat {
            return mInstance
        }
    }

    fun showToast(text: CharSequence) {
        if (DataCompat.isEmpty(text)) {
            return
        }
        try {
            if (mToast == null) {
                mToast = Toast.makeText(DataCompat.getContext(), text, Toast.LENGTH_SHORT)
            } else {
                mToast?.setText(text)
            }
            mToast?.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun showToast(@StringRes resText: Int) {
        try {
            if (mToast == null) {
                mToast = Toast.makeText(DataCompat.getContext(), resText, Toast.LENGTH_SHORT)
            } else {
                mToast?.setText(resText)
            }
            mToast?.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

}