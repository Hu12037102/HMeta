package com.sbnh.my.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sbnh.comm.base.viewmodel.BaseViewModel
import com.sbnh.comm.entity.base.BasePagerEntity
import com.sbnh.comm.entity.my.MyCollectionEntity
import com.sbnh.comm.entity.request.RequestPagerListEntity
import com.sbnh.my.MyService
import kotlinx.coroutines.launch

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/14 16:58
 * 更新时间: 2022/6/14 16:58
 * 描述:我的藏品ViewModel
 */
class MyCollectionViewModel : BaseViewModel() {

    val mCollectionLiveData = MutableLiveData<BasePagerEntity<List<MyCollectionEntity>>>()

    fun loadCollectionList(requestPagerListEntity: RequestPagerListEntity) {
        viewModelScope.launch {
            try {
                val result = mRetrofitManger.create(MyService::class.java)
                    .loadMyCollectionList(requestPagerListEntity)
                disposeRetrofit(mCollectionLiveData, result)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}