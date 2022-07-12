package com.sbnh.home.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sbnh.comm.base.viewmodel.BaseViewModel
import com.sbnh.comm.entity.base.BasePagerEntity
import com.sbnh.comm.entity.home.CollectionEntity
import com.sbnh.comm.entity.home.HomeBannerEntity
import com.sbnh.comm.entity.request.RequestPagerListEntity
import com.sbnh.comm.utils.LogUtils
import com.sbnh.home.HomeService
import kotlinx.coroutines.launch

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/13 16:16
 * 更新时间: 2022/6/13 16:16
 * 描述:
 */
class HomeViewModel : BaseViewModel() {
    val mCollectionLiveData = MutableLiveData<BasePagerEntity<List<CollectionEntity>>>()
    val mBannerLiveData = MutableLiveData<List<HomeBannerEntity>>()
    fun loadCollectionList(requestPagerListEntity: RequestPagerListEntity) {
        viewModelScope.launch {
            val result = try {
                mRetrofitManger.create(HomeService::class.java)
                    .loadCollectionList(requestPagerListEntity)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
            disposeRetrofit(mCollectionLiveData, result, true)
        }
    }

    fun loadBanner() {
        viewModelScope.launch {
            val result = try {
                mRetrofitManger.create(HomeService::class.java)
                    .loadHomeBanner()
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
            disposeRetrofit(mBannerLiveData, result)
        }
    }
}