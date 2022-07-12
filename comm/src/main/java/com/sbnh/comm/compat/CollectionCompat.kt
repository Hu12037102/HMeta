package com.sbnh.comm.compat

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/12 16:00
 * 更新时间: 2022/6/12 16:00
 * 描述:
 */
object CollectionCompat {
    @JvmStatic
    fun <T> isEmptyList(list: List<T>?): Boolean = list == null || list.isEmpty()

    @JvmStatic
    fun getListSize(list: List<*>?): Int = if (isEmptyList(list)) 0 else list!!.size

    @JvmStatic
    fun <T> notEmptyList(list: List<T>?): Boolean = getListSize(list) > 0

    @JvmStatic
    fun <T> addAll(parentList: ArrayList<T>?, childList: List<T>?) {
        if (parentList == null || childList == null)
            return
        parentList.addAll(childList)
    }
}