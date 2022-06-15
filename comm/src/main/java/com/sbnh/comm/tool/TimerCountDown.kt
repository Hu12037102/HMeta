package com.sbnh.comm.tool

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.sbnh.comm.compat.DataCompat

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/15 11:19
 * 更新时间: 2022/6/15 11:19
 * 描述:
 */
abstract class TimerCountDown(time: Long, interval: Long) : CountDownTimer(time, interval),
    LifecycleEventObserver {
    private var isRunning = false
    fun isRunning(): Boolean = isRunning
    private val mLifecycles = ArrayList<Lifecycle>()
    fun addLifecycle(lifecycle: Lifecycle?) {
        lifecycle?.let {
            mLifecycles.add(it)
            it.addObserver(this)
        }
    }

    private fun removeLifecycle(lifecycle: Lifecycle?) {
        lifecycle?.let {
            if (mLifecycles.contains(it)) {
                mLifecycles.remove(it)
                it.removeObserver(this)
            }
        }
    }


    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (event.targetState == Lifecycle.State.DESTROYED) {
            this.cancels()
            removeLifecycle(source.lifecycle)
        }
    }

    override fun onFinish() {
        isRunning = false
        Log.w("TimerCountDown", "我完成了倒计时")
    }

    override fun onTick(millisUntilFinished: Long) {
        isRunning = true
        Log.w("TimerCountDown", "我在倒计时：${millisUntilFinished / 1000}")
    }

    fun starts() {
        start()
        isRunning = true
        Log.w("TimerCountDown", "我开始倒计时")
    }

    fun cancels() {
        cancel()
        isRunning = false
        Log.w("TimerCountDown", "我取消倒计时")
    }

}