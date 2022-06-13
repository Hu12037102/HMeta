package com.sbnh.comm.manger

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/13 15:48
 * 更新时间: 2022/6/13 15:48
 * 描述:
 */
class ActivityCompatManger private constructor() {
    companion object {
        val mInstance: ActivityCompatManger by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { ActivityCompatManger() }
    }


}