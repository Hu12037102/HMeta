package com.sbnh.my.viewmodel

import com.sbnh.comm.base.viewmodel.BaseViewModel
import com.sbnh.comm.compat.DataCompat
import com.sbnh.comm.entity.my.ALL
import com.sbnh.comm.entity.my.GET
import com.sbnh.comm.entity.my.GIVE
import com.sbnh.comm.entity.my.GiveCollectionTabEntity

class GiveCollectionListViewModel : BaseViewModel() {
    fun createTab(): List<GiveCollectionTabEntity> {
        val list = ArrayList<GiveCollectionTabEntity>()
        list.add(GiveCollectionTabEntity(ALL, DataCompat.getResString(com.sbnh.comm.R.string.all), true))
        list.add(GiveCollectionTabEntity(GIVE, DataCompat.getResString(com.sbnh.comm.R.string.give_type_give), false))
        list.add(GiveCollectionTabEntity(GET, DataCompat.getResString(com.sbnh.comm.R.string.give_type_get), false))
        return list
    }
}