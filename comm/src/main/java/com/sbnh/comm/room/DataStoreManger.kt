package com.sbnh.comm.room

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/13 20:06
 * 更新时间: 2022/6/13 20:06
 * 描述:
 */
class DataStoreManger private constructor() {
    companion object {
        private val mInstance: DataStoreManger by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { DataStoreManger() }
        fun get(): DataStoreManger {
            return mInstance
        }
    }


}