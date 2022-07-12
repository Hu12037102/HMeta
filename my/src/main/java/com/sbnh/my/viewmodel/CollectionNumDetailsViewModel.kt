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

class CollectionNumDetailsViewModel(private val mMyCollectionRepo: MyCollectionRepository = MyCollectionRepository()) :
    BaseViewModel() {

    val mCollectionNumDetailsLiveData =
        MutableLiveData<BasePagerEntity<List<CollectionNumDetailsEntity>>>()
    val mCachedCollectionNumDetailsLiveData =
        MutableLiveData<BasePagerEntity<List<CollectionNumDetailsEntity>>?>()

    fun loadCollectionNumDetailsList(entity: RequestCollectionNumDetailsEntity) {
        viewModelScope.launch {
            val result = try {
                mRetrofitManger.create(MyService::class.java)
                    .loadCollectionNumDetails(entity)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
            disposeRetrofit(mCollectionNumDetailsLiveData, result)
        }
    }

    fun cacheCollectionNumDetailsPagerEntity(
        id: String,
        data: BasePagerEntity<List<CollectionNumDetailsEntity>>?
    ) {
        viewModelScope.launch {
            mMyCollectionRepo.cacheCollectionNumDetailsPagerEntity(id, data)
        }
    }

    fun loadCachedCollectionNumDetailsPagerEntity(id: String) {
        viewModelScope.launch {
            mCachedCollectionNumDetailsLiveData.value =
                mMyCollectionRepo.loadCachedCollectionNumDetailsPagerEntity(id)
        }
    }

}