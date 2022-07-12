package com.sbnh.my.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sbnh.comm.base.viewmodel.BaseViewModel
import com.sbnh.comm.entity.base.BasePagerEntity
import com.sbnh.comm.entity.my.GiveCollectionEntity
import com.sbnh.comm.entity.request.RequestGiveCollectionListEntity
import com.sbnh.my.MyService
import com.sbnh.my.repo.MyCollectionRepository
import kotlinx.coroutines.launch

class GiveCollectionContentViewModel(private val mMyCollectionRepo: MyCollectionRepository = MyCollectionRepository()) :
    BaseViewModel() {

    val mGiveCollectionListLiveData = MutableLiveData<BasePagerEntity<List<GiveCollectionEntity>>>()
    val mCachedGiveCollectionListLiveData =
        MutableLiveData<BasePagerEntity<List<GiveCollectionEntity>>?>()

    fun giveCollectionList(entity: RequestGiveCollectionListEntity) {
        viewModelScope.launch {
            val result = try {
                mRetrofitManger.create(MyService::class.java)
                    .loadGiveCollectionList(entity)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
            disposeRetrofit(mGiveCollectionListLiveData, result)
        }
    }

    fun cacheGiveCollectionPagerEntity(
        giveType: Int,
        data: BasePagerEntity<List<GiveCollectionEntity>>?
    ) {
        viewModelScope.launch {
            mMyCollectionRepo.cacheGiveCollectionPagerEntity(giveType, data)
        }
    }

    fun loadCachedGiveCollectionPagerEntity(giveType: Int) {
        viewModelScope.launch {
            mCachedGiveCollectionListLiveData.value =
                mMyCollectionRepo.loadCachedGiveCollectionPagerEntity(giveType)
        }
    }

}