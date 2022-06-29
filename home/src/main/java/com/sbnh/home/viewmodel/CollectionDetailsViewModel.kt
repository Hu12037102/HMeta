package com.sbnh.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sbnh.comm.base.viewmodel.BaseOrderViewModel
import com.sbnh.comm.entity.home.CollectionEntity
import com.sbnh.home.HomeService
import kotlinx.coroutines.launch

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/20 9:35
 * 更新时间: 2022/6/20 9:35
 * 描述:藏品详情model
 */
class CollectionDetailsViewModel : BaseOrderViewModel() {
    val mCollectionDetailsLiveData = MutableLiveData<CollectionEntity>()

    fun loadCollectionDetails(id: String) {
        viewModelScope.launch {
            try {
                val result = mRetrofitManger.create(HomeService::class.java)
                    .loadCollectionDetails(id)
                disposeRetrofit(mCollectionDetailsLiveData, result,true)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * 加载已收藏商品详情
     */
    fun loadKnapsackCollectionDetails(id: String, cid: String) {
        viewModelScope.launch {
            try {
                val result = mRetrofitManger.create(HomeService::class.java)
                    .loadKnapsackCollectionDetails(cid, id)
                disposeRetrofit(mCollectionDetailsLiveData, result,true)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}