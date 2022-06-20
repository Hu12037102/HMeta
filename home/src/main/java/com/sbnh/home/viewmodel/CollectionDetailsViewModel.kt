package com.sbnh.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sbnh.comm.base.viewmodel.BaseViewModel
import com.sbnh.comm.entity.home.CollectionEntity
import com.sbnh.home.HomeService
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.nio.charset.Charset

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/20 9:35
 * 更新时间: 2022/6/20 9:35
 * 描述:藏品详情model
 */
class CollectionDetailsViewModel : BaseViewModel() {
    val mCollectionDetailsLiveData = MutableLiveData<CollectionEntity>()
    fun loadCollectionDetails(id: String) {
        viewModelScope.launch {
            try {
                val result = mRetrofitManger.create(HomeService::class.java)
                    .loadCollectionDetails(id)
                disposeRetrofit(mCollectionDetailsLiveData, result)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}