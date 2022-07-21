package com.sbnh.bazaar.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sbnh.bazaar.BazaarService
import com.sbnh.comm.base.viewmodel.BaseViewModel
import com.sbnh.comm.entity.base.BasePagerEntity
import com.sbnh.comm.entity.bazaar.BazaarDataEntity
import com.sbnh.comm.entity.request.RequestBazaarDataEntity
import kotlinx.coroutines.launch

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/7/12 17:13
 * 更新时间: 2022/7/12 17:13
 * 描述:
 */
class BazaarDetailsContentViewModel : BaseViewModel() {
    val mDataLiveData = MutableLiveData<BasePagerEntity<List<BazaarDataEntity>>>()
    fun loadBazaarDetailsList(entity: RequestBazaarDataEntity) {
        viewModelScope.launch {
            showLoading()
            val result = try {
                mRetrofitManger.create(BazaarService::class.java)
                    .loadBazaarDataList(entity)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
            disposeRetrofit(mDataLiveData, result, true)
            dismissLoading()
        }
    }
}