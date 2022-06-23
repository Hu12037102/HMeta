package com.sbnh.comm.dialog.viewmodel

import com.sbnh.comm.entity.pay.PaymentPasswordNumberEntity
import com.sbnh.comm.entity.pay.SOFT_TYPE_DELETE
import com.sbnh.comm.entity.pay.SOFT_TYPE_NUMBER
import com.sbnh.comm.entity.pay.SOFT_TYPE_UNKNOWN
import com.sbnh.comm.viewmodel.BaseDialogViewModel

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/23 17:52
 * 更新时间: 2022/6/23 17:52
 * 描述:
 */
class DialogSetPaymentPasswordViewModel : BaseDialogViewModel() {
    fun getPasswordNumberData(): List<PaymentPasswordNumberEntity> {
        val list = ArrayList<PaymentPasswordNumberEntity>()
        list.add(PaymentPasswordNumberEntity(SOFT_TYPE_NUMBER, "1", 1))
        list.add(PaymentPasswordNumberEntity(SOFT_TYPE_NUMBER, "2", 2))
        list.add(PaymentPasswordNumberEntity(SOFT_TYPE_NUMBER, "3", 3))
        list.add(PaymentPasswordNumberEntity(SOFT_TYPE_NUMBER, "4", 4))
        list.add(PaymentPasswordNumberEntity(SOFT_TYPE_NUMBER, "5", 5))
        list.add(PaymentPasswordNumberEntity(SOFT_TYPE_NUMBER, "6", 6))
        list.add(PaymentPasswordNumberEntity(SOFT_TYPE_NUMBER, "7", 7))
        list.add(PaymentPasswordNumberEntity(SOFT_TYPE_NUMBER, "8", 8))
        list.add(PaymentPasswordNumberEntity(SOFT_TYPE_NUMBER, "9", 9))
        list.add(PaymentPasswordNumberEntity(SOFT_TYPE_UNKNOWN, null, null))
        list.add(PaymentPasswordNumberEntity(SOFT_TYPE_NUMBER, "0", 0))
        list.add(
            PaymentPasswordNumberEntity(
                SOFT_TYPE_DELETE,
                null,
                null,
                com.sbnh.comm.R.mipmap.icon_pay_soft_delete
            )
        )
        return list
    }
}