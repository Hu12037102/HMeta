package com.sbnh.bazaar.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sbnh.bazaar.BazaarService
import com.sbnh.comm.base.viewmodel.BaseViewModel
import com.sbnh.comm.entity.bazaar.BazaarTabEntity
import kotlinx.coroutines.launch
import retrofit2.Response

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/13 16:40
 * 更新时间: 2022/6/13 16:40
 * 描述:
 */
class BazaarViewModel : BaseViewModel() {
    val mTabLiveData = MutableLiveData<List<BazaarTabEntity>>()
    fun loadTabs() {
        viewModelScope.launch {
            var result: Response<List<BazaarTabEntity>>?
            try {
                result = mRetrofitManger.create(BazaarService::class.java)
                    .loadBazaarTabs()
                result.body()?.get(0)?.isSelector = true
            } catch (e: Exception) {
                e.printStackTrace()
                result = null
            }
            disposeRetrofit(mTabLiveData, result)

        }
    }
}