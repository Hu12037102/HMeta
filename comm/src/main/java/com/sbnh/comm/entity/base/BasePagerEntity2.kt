package com.sbnh.comm.entity.base

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/22 12:48
 * 更新时间: 2022/6/22 12:48
 * 描述:
 */
data class BasePagerEntity2<T>(var list: T?) {
    var count: Int? = 0
    var countSize: Int? = 0
    var page: Int? = 0
    var size: Int? = 0

    companion object {
        @JvmStatic
        fun <T> getData(entity2: BasePagerEntity2<T>?): T? = entity2?.list
    }

}
