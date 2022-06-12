package com.sbnh.healermeta.viewmodel

import com.sbnh.comm.base.viewmodel.BaseViewModel
import com.sbnh.comm.compat.DataCompat
import com.sbnh.comm.entity.base.SelectorTabEntity
import com.sbnh.comm.R

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/10 14:20
 * 更新时间: 2022/6/10 14:20
 * 描述:
 */
class MainViewModel : BaseViewModel() {
    fun createBottomTabs(): ArrayList<SelectorTabEntity> {
        val data = ArrayList<SelectorTabEntity>()
        data.add(SelectorTabEntity(DataCompat.getResString(R.string.home),true))
        data.add(SelectorTabEntity(DataCompat.getResString(R.string.bazaar),false))
        data.add(SelectorTabEntity(DataCompat.getResString(R.string.my),false))
        return data
    }


}