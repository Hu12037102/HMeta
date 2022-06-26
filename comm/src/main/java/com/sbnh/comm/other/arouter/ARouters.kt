package com.sbnh.comm.other.arouter

import android.app.Activity
import android.content.Intent
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.launcher.ARouter

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/13 15:57
 * 更新时间: 2022/6/13 15:57
 * 描述:
 */
object ARouters {

    @JvmStatic
    fun startActivity(path: String) {
        try {
            build(path).navigation()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    @JvmStatic
    fun startActivityForResult(path: String, activity: Activity, requestCode: Int) {
        try {
            build(path).navigation(activity, requestCode)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @JvmStatic
    fun build(path: String): Postcard {
        val flag = Intent.FLAG_ACTIVITY_SINGLE_TOP; Intent.FLAG_ACTIVITY_CLEAR_TOP
        return ARouter.getInstance().build(path)
            .withFlags(flag)
    }

    @JvmStatic
    fun getFragment(path: String): Any {
        return  build(path).navigation()
    }


}