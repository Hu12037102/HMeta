package com.sbnh.comm.manger

import android.app.Activity
import androidx.collection.ArrayMap
import com.sbnh.comm.compat.DataCompat
import com.sbnh.comm.other.arouter.ARouterConfig
import com.sbnh.comm.other.arouter.ARouters

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

    private val mArrayMap: ArrayMap<String, Activity> by lazy { ArrayMap() }
    fun add(activity: Activity) {
        mArrayMap[activity.javaClass.simpleName] = activity
    }

    private fun remove(name: String) {
        val activity = mArrayMap[name]
        if (DataCompat.notNull(activity)) {
            if (activity?.isFinishing != true) {
                activity?.finish()
            }
            mArrayMap.remove(name)
        }
    }

    fun remove(activity: Activity) {
        remove(activity.javaClass.simpleName)
    }

    fun clear() {
        val iterator = mArrayMap.iterator()
        while (iterator.hasNext()) {
            val entity: MutableMap.MutableEntry<String, Activity> = iterator.next()
            if (!entity.value.isFinishing) {
                entity.value.finish()
            }
            iterator.remove()
        }
    }


}