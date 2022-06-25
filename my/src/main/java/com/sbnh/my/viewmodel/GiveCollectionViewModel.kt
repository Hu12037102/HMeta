package com.sbnh.my.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sbnh.comm.base.viewmodel.BaseViewModel
import com.sbnh.comm.entity.request.RequestGiveCollectionEntity
import com.sbnh.my.MyService
import kotlinx.coroutines.launch

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/17 15:31
 * 更新时间: 2022/6/17 15:31
 * 描述:赠送藏品契约
 */
class GiveCollectionViewModel :BaseViewModel(){

    val mGiveCollectionLiveData: MutableLiveData<Unit> = MutableLiveData()

    fun giveCollection(entity: RequestGiveCollectionEntity) {
        viewModelScope.launch {
            try {
                val result = mRetrofitManger.create(MyService::class.java)
                    .giveCollection(entity)
                disposeRetrofit(mGiveCollectionLiveData, result)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}