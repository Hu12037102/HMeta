package com.sbnh.my.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sbnh.comm.base.viewmodel.BaseViewModel
import com.sbnh.comm.entity.base.BasePagerEntity
import com.sbnh.comm.entity.my.CollectionNumDetailsEntity
import com.sbnh.comm.entity.request.RequestCollectionNumDetailsEntity
import com.sbnh.my.MyService
import com.sbnh.my.repo.MyCollectionRepository
import kotlinx.coroutines.launch

class CollectionNumDetailsViewModel(private val mMyCollectionRepo: MyCollectionRepository = MyCollectionRepository()) : BaseViewModel() {

    val mCollectionNumDetailsLiveData = MutableLiveData<BasePagerEntity<List<CollectionNumDetailsEntity>>>()
    val mCachedCollectionNumDetailsLiveData = MutableLiveData<BasePagerEntity<List<CollectionNumDetailsEntity>>?>()

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

    fun cacheCollectionNumDetailsPagerEntity(data: BasePagerEntity<List<CollectionNumDetailsEntity>>?) {
        viewModelScope.launch {
            mMyCollectionRepo.cacheCollectionNumDetailsPagerEntity(data)
        }
    }

    fun loadCachedCollectionNumDetailsPagerEntity() {
        viewModelScope.launch {
            mCachedCollectionNumDetailsLiveData.value = mMyCollectionRepo.loadCachedCollectionNumDetailsPagerEntity()
        }
    }

}