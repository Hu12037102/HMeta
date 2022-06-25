package com.sbnh.my.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sbnh.comm.base.viewmodel.BaseViewModel
import com.sbnh.comm.entity.base.BasePagerEntity
import com.sbnh.comm.entity.my.CollectionNumDetailsEntity
import com.sbnh.comm.entity.my.MyCollectionEntity
import com.sbnh.comm.entity.request.RequestCollectionNumDetailsEntity
import com.sbnh.comm.entity.request.RequestPagerListEntity
import com.sbnh.my.MyService
import kotlinx.coroutines.launch

class CollectionNumDetailsViewModel : BaseViewModel() {

    val mCollectionNumDetailsLiveData = MutableLiveData<BasePagerEntity<List<CollectionNumDetailsEntity>>>()

    fun loadCollectionNumDetailsList(entity: RequestCollectionNumDetailsEntity) {
        viewModelScope.launch {
            try {
                val result = mRetrofitManger.create(MyService::class.java)
                    .loadCollectionNumDetails(entity)
                disposeRetrofit(mCollectionNumDetailsLiveData, result)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}