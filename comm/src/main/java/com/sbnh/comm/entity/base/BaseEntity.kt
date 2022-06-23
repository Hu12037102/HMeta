package com.sbnh.comm.entity.base

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/21 13:44
 * 更新时间: 2022/6/21 13:44
 * 描述:
 */
data class BaseEntity<T>(var data: T?) {
    var code: String = ""
    var error: String? = ""
    var message: String? = ""
    var msg: String? = ""

    companion object {
        @JvmStatic
        fun <T> getData(entity: BaseEntity<T>?) = entity?.data

        @JvmStatic
        fun <T> getPagerData(entity: BaseEntity<BasePagerEntity2<T>>?): T? = entity?.data?.list
    }
}
