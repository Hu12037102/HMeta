package com.sbnh.comm.manger

import android.app.Activity
import androidx.collection.ArrayMap
import com.sbnh.comm.compat.DataCompat
import com.sbnh.comm.other.arouter.ARouterConfig

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/13 15:48
 * 更新时间: 2022/6/13 15:48
 * 描述:
 */
class ActivityCompatManger private constructor() {
    companion object {
        private val mInstance: ActivityCompatManger by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { ActivityCompatManger() }

        @JvmStatic
        fun get() = mInstance


    }

    private val mList: ArrayList<Activity> by lazy { ArrayList() }
    fun add(activity: Activity) {
        mList.add(activity)
    }

    private fun remove(name: String) {
        val iterator = mList.iterator()
        while (iterator.hasNext()) {
            val activity = iterator.next()
            if (activity.javaClass.simpleName.contains(
                    name,
                    true
                ) || activity.javaClass.simpleName.equals(
                    name,
                    true
                )
            ) {
                if (!activity.isFinishing || !activity.isDestroyed) {
                    activity.finish()
                }
                iterator.remove()
            }
        }

    }

    fun remove(activity: Activity) {
        remove(activity.javaClass.simpleName)
    }

    fun clear() {
        val iterator = mList.iterator()
        while (iterator.hasNext()) {
            val entity = iterator.next()
            if (!entity.isFinishing || !entity.isDestroyed) {
                entity.finish()
            }
            iterator.remove()
        }
    }

    fun removeLoginAndRegisterActivity() {
        remove(ARouterConfig.Value.LOGIN_ACTIVITY)
        remove(ARouterConfig.Value.REGISTER_ACTIVITY)
    }

}