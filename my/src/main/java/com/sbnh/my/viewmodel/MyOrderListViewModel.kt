package com.sbnh.my.viewmodel

import com.sbnh.comm.base.viewmodel.BaseViewModel
import com.sbnh.comm.compat.DataCompat
import com.sbnh.comm.entity.order.*

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/21 19:35
 * 更新时间: 2022/6/21 19:35
 * 描述:
 */
class MyOrderListViewModel : BaseViewModel() {
    fun createOrderTab(): List<OrderTabEntity> {
        val list = ArrayList<OrderTabEntity>()
        list.add(OrderTabEntity(null, DataCompat.getResString(com.sbnh.comm.R.string.all), true))
        list.add(
            OrderTabEntity(
                STATUS_WAIT_PAY,
                DataCompat.getResString(com.sbnh.comm.R.string.wait_pays)
            )
        )
        list.add(
            OrderTabEntity(
                STATUS_COMPLETE,
                DataCompat.getResString(com.sbnh.comm.R.string.account_paid)
            )
        )
        list.add(
            OrderTabEntity(
                STATUS_CANCEL,
                DataCompat.getResString(com.sbnh.comm.R.string.cancelled)
            )
        )
        return list
    }
}