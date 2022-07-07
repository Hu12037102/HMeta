package com.sbnh.my.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sbnh.comm.base.viewmodel.BaseViewModel
import com.sbnh.comm.entity.my.CompoundPagerEntity
import com.sbnh.my.MyService
import kotlinx.coroutines.launch

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/7/6 11:17
 * 更新时间: 2022/7/6 11:17
 * 描述:
 */
class CompoundPagerViewModel : BaseViewModel() {
    val mCompoundPagerLiveData = MutableLiveData<CompoundPagerEntity>()
    fun loadCompoundPagerDetails(id: String) {
        viewModelScope.launch {
            try {
                val result = mRetrofitManger.create(MyService::class.java)
                    .loadCompoundPagerDetails(id)
                disposeRetrofit(mCompoundPagerLiveData, result)

            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }
}