package com.sbnh.comm.base.viewmodel

import androidx.lifecycle.MutableLiveData
import com.sbnh.comm.Contract
import com.sbnh.comm.entity.base.ResultDownTimeEntity
import com.sbnh.comm.entity.base.STATUS_ERROR
import com.sbnh.comm.entity.base.STATUS_FINISH
import com.sbnh.comm.entity.base.STATUS_RUNNING
import com.sbnh.comm.tool.TimerCountDown
import com.sbnh.comm.utils.LogUtils
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/15 13:37
 * 更新时间: 2022/6/15 13:37
 * 描述:
 */
open class TimerViewModel : BaseViewModel() {
    private var mDisposable: Disposable? = null
    val mTimerLiveData: MutableLiveData<ResultDownTimeEntity> by lazy { MutableLiveData<ResultDownTimeEntity>() }
    fun downTimer(timeLength: Long) {
        Observable.interval(0, 1, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .take(timeLength)
            .map { timeLength - it }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Long> {
                override fun onSubscribe(d: Disposable) {
                    this@TimerViewModel.mDisposable = d
                    LogUtils.w("downTimer--", "onSubscribe--")
                }

                override fun onNext(t: Long) {
                    mTimerLiveData.value = ResultDownTimeEntity(STATUS_RUNNING, t)
                    LogUtils.w("downTimer--", "onNext--$t")
                }

                override fun onError(e: Throwable) {
                    mTimerLiveData.value = ResultDownTimeEntity(STATUS_ERROR)
                    LogUtils.w("downTimer--", "onError--$e")
                }

                override fun onComplete() {
                    mTimerLiveData.value = ResultDownTimeEntity(STATUS_FINISH)
                    LogUtils.w("downTimer--", "onComplete--")
                }

            })
    }

    override fun onCleared() {
        super.onCleared()
        mDisposable?.let {
            if (!it.isDisposed) {
                it.dispose()
                LogUtils.w("downTimer--", "onCleared--")
            }
        }
    }
}