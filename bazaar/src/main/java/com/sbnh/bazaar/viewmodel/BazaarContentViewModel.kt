package com.sbnh.bazaar.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sbnh.bazaar.BazaarService
import com.sbnh.comm.base.viewmodel.BaseViewModel
import com.sbnh.comm.entity.base.BasePagerEntity
import com.sbnh.comm.entity.bazaar.BazaarEntity
import com.sbnh.comm.entity.request.RequestBazaarEntity
import com.sbnh.comm.utils.LogUtils
import kotlinx.coroutines.launch

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/7/4 9:38
 * 更新时间: 2022/7/4 9:38
 * 描述:
 */
class BazaarContentViewModel : BaseViewModel() {
    val mDataLiveData = MutableLiveData<BasePagerEntity<List<BazaarEntity>>>()
    fun loadBazaarList(entity: RequestBazaarEntity) {
        viewModelScope.launch {
            val result = try {
                mRetrofitManger.create(BazaarService::class.java)
                    .loadBazaarContentList(entity)

            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
            disposeRetrofit(mDataLiveData, result,true)

        }

    }
}