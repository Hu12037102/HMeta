package com.sbnh.bazaar.viewmodel

import com.sbnh.comm.base.viewmodel.BaseViewModel
import com.sbnh.comm.compat.DataCompat
import com.sbnh.comm.entity.base.BaseEntity
import com.sbnh.comm.entity.base.SelectorTabEntity
import com.sbnh.comm.entity.base.TabEntity

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/7/12 15:26
 * 更新时间: 2022/7/12 15:26
 * 描述:
 */
class BazaarDetailsViewModel : BaseViewModel() {

    fun getTabs(): List<SelectorTabEntity> {
        val tabs = ArrayList<SelectorTabEntity>()
        val saleEntity = SelectorTabEntity(
            DataCompat.getResString(com.sbnh.comm.R.string.bazaar),
            true,
            com.sbnh.comm.R.drawable.shape_click_view,
            SelectorTabEntity.TYPE_BAZAAR
        )
        tabs.add(saleEntity)
        val soldEntity = SelectorTabEntity(
            DataCompat.getResString(com.sbnh.comm.R.string.been_sold),
            false,
            com.sbnh.comm.R.drawable.shape_click_view,0,
            SelectorTabEntity.TYPE_BEEN_SOLD
        )
        tabs.add(soldEntity)
        return tabs
    }
}