package com.sbnh.comm.other.tencent

import android.content.Context
import com.google.gson.Gson
import com.sbnh.comm.BuildConfig
import com.sbnh.comm.entity.other.CaptchaCheckResultEntity
import com.sbnh.comm.utils.LogUtils
import com.tencent.captchasdk.TCaptchaDialog

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/22 17:30
 * 更新时间: 2022/6/22 17:30
 * 描述:
 */
class CaptchaDialogHelper {

    companion object {
        @JvmStatic
        fun showDialog(context: Context, callback: OnDialogCallback?) {
            val dialog = TCaptchaDialog(
                context, BuildConfig.TENCENT_CAPTCHA_APP_ID,
                {
                    try {
                        val entity =
                            Gson().fromJson(it.toString(), CaptchaCheckResultEntity::class.java)
                        callback?.onResult(entity)
                        LogUtils.w("showDialog--", "$entity")
                    } catch (e: Exception) {
                        e.printStackTrace()
                        callback?.onResult(null)
                    }
                }, null
            )
            dialog.show()
        }
    }

    interface OnDialogCallback {
        fun onResult(entity: CaptchaCheckResultEntity?)
    }

}