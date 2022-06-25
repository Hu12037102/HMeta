package com.sbnh.my.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sbnh.comm.base.viewmodel.BaseViewModel
import com.sbnh.comm.entity.base.BasePagerEntity
import com.sbnh.comm.entity.my.GiveCollectionEntity
import com.sbnh.comm.entity.request.RequestGiveCollectionListEntity
import com.sbnh.my.MyService
import kotlinx.coroutines.launch

class GiveCollectionContentViewModel :BaseViewModel(){

    val mGiveCollectionListLiveData = MutableLiveData<BasePagerEntity<List<GiveCollectionEntity>>>()

    fun giveCollectionList(entity: RequestGiveCollectionListEntity) {
        viewModelScope.launch {
            try {
                val result = mRetrofitManger.create(MyService::class.java)
                    .loadGiveCollectionList(entity)
                disposeRetrofit(mGiveCollectionListLiveData, result)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}