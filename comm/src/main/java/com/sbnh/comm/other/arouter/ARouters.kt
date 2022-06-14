package com.sbnh.comm.other.arouter

import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.launcher.ARouter
import com.sbnh.comm.compat.DataCompat

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
            ARouter.getInstance().build(path).navigation()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    @JvmStatic
    fun build(path: String): Postcard{
        return ARouter.getInstance().build(path)
    }

    @JvmStatic
    fun getFragment(path: String): Any {
        return ARouter.getInstance().build(path).navigation()
    }




}