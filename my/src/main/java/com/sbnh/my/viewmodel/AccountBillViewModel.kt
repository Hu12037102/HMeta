package com.sbnh.my.viewmodel

import com.sbnh.comm.base.viewmodel.BaseViewModel
import com.sbnh.comm.compat.DataCompat
import com.sbnh.comm.entity.base.SelectorTabEntity

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/7/14 17:21
 * 更新时间: 2022/7/14 17:21
 * 描述:
 */
class AccountBillViewModel : BaseViewModel() {
    fun getTabs() :List<SelectorTabEntity>{
        val entityAll = SelectorTabEntity(
            DataCompat.getResString(com.sbnh.comm.R.string.all),
            true,
            0,
            0,
            SelectorTabEntity.AccountBill.TYPE_ALL
        )
        val entityExpend = SelectorTabEntity(
            DataCompat.getResString(com.sbnh.comm.R.string.expend),
            false,
            0,
            0,
            SelectorTabEntity.AccountBill.TYPE_EXPEND
        )
        val entityRevenues = SelectorTabEntity(
            DataCompat.getResString(com.sbnh.comm.R.string.revenues),
            false,
            0,
            0,
            SelectorTabEntity.AccountBill.TYPE_REVENUES
        )
        val entityTopUp = SelectorTabEntity(
            DataCompat.getResString(com.sbnh.comm.R.string.top_up),
            false,
            0,
            0,
            SelectorTabEntity.AccountBill.TYPE_TOP_UP
        )
        val entityWithdraw = SelectorTabEntity(
            DataCompat.getResString(com.sbnh.comm.R.string.withdraw),
            false,
            0,
            0,
            SelectorTabEntity.AccountBill.TYPE_WITHDRAW
        )
        return arrayListOf(entityAll,entityExpend,entityRevenues,entityTopUp,entityWithdraw)
    }
}