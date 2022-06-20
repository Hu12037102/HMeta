package com.sbnh.comm.entity.base

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/20 14:53
 * 更新时间: 2022/6/20 14:53
 * 描述:
 */
data class BasePagerEntity<T>(val data: T?) {
    var pageNum: Int? = 0
    var pageSize: Int? = 0
    var pages: Int? = 0
    var total: Int? = 0

    companion object {
        @JvmStatic
        fun <T> getData(entity: BasePagerEntity<T>?): T? {
            return entity?.data
        }
    }
}