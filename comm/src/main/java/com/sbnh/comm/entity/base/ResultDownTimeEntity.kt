package com.sbnh.comm.entity.base

import androidx.annotation.IntDef

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/15 13:49
 * 更新时间: 2022/6/15 13:49
 * 描述:
 */
@IntDef(STATUS_RUNNING, STATUS_DEFAULT, STATUS_ERROR,STATUS_FINISH)
@Retention(AnnotationRetention.SOURCE)
annotation class TimerStatus

const val STATUS_DEFAULT = 0
const val STATUS_RUNNING = 1
const val STATUS_ERROR = -1
const val STATUS_FINISH = 2

data class ResultDownTimeEntity(
    @TimerStatus var status: Int = STATUS_DEFAULT,
    val lastTimeLength: Long = 0
) {

}
