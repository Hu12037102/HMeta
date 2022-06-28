package com.sbnh.comm.weight.click

import android.view.View
import com.sbnh.comm.utils.LogUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/15 9:24
 * 更新时间: 2022/6/15 9:24
 * 描述:
 */
abstract class DelayedClick : View.OnClickListener {
    private var mTimeLength = 1000
    private var mLastTimeMills = 0L

    constructor()
    constructor(timeLength: Int) {
        this.mTimeLength = timeLength
    }

    override fun onClick(v: View?) {
        val newTimeMillis = System.currentTimeMillis()
        if ((newTimeMillis - mLastTimeMills) > mTimeLength) {
            onDelayedClick(v)
            LogUtils.w(
                "onClick--",
                "我被点击$mLastTimeMills-----$newTimeMillis---${(newTimeMillis - mTimeLength)}"
            )
        }
        mLastTimeMills = newTimeMillis

    }

    abstract fun onDelayedClick(v: View?)
}