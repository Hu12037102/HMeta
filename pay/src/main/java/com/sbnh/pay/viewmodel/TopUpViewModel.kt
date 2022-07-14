package com.sbnh.pay.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sbnh.comm.base.viewmodel.BaseViewModel
import com.sbnh.comm.entity.base.BaseEntity
import com.sbnh.comm.entity.pay.TopUpBeforeEntity
import com.sbnh.comm.entity.request.RequestTopUpAfterEntity
import com.sbnh.comm.entity.request.RequestTopUpBeforeEntity
import com.sbnh.pay.PayService
import kotlinx.coroutines.launch

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/7/13 16:49
 * 更新时间: 2022/7/13 16:49
 * 描述:
 */
class TopUpViewModel : BaseViewModel() {
    val mTopUpBeforeLiveData = MutableLiveData<BaseEntity<TopUpBeforeEntity>>()
    val mTopUpAfterLiveData = MutableLiveData<BaseEntity<Unit>>()
    fun topUpMoneyBefore(entity: RequestTopUpBeforeEntity) {
        viewModelScope.launch {
            val result = try {
                mRetrofitManger.create(PayService::class.java)
                    .topUpMoneyBefore(entity)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
            disposeRetrofit(mTopUpBeforeLiveData, result)
        }
    }

    fun topUpMoneyAfter(entity: RequestTopUpAfterEntity) {
        viewModelScope.launch {
            val result = try {
                mRetrofitManger.create(PayService::class.java)
                    .topUpMoneyAfter(entity)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
            disposeRetrofit(mTopUpAfterLiveData,result)
        }
    }
}