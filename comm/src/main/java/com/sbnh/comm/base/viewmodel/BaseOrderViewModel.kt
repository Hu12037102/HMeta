package com.sbnh.comm.base.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sbnh.comm.entity.base.BaseEntity
import com.sbnh.comm.entity.order.OrderEntity
import com.sbnh.comm.entity.order.ResultCommitOrderEntity
import com.sbnh.comm.entity.request.RequestCancelOrderEntity
import com.sbnh.comm.entity.request.RequestCreateOrderEntity
import com.sbnh.comm.http.BaseService
import kotlinx.coroutines.launch

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/21 14:13
 * 更新时间: 2022/6/21 14:13
 * 描述:
 */
open class BaseOrderViewModel : TimerViewModel() {
    val mCommitOrderLiveData: MutableLiveData<BaseEntity<ResultCommitOrderEntity>> by lazy { MutableLiveData() }
    val mOrderDetailsLiveData: MutableLiveData<BaseEntity<OrderEntity>> by lazy { MutableLiveData() }
    val mCancelOrderLiveData: MutableLiveData<BaseEntity<Unit>> by lazy { MutableLiveData() }
    fun commitOrder(entity: RequestCreateOrderEntity) {
        viewModelScope.launch {
            try {
                val result = mRetrofitManger.create(BaseService::class.java)
                    .commitOrder(entity)
                disposeRetrofit(mCommitOrderLiveData, result)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun queryOrderDetails(id: String) {
        viewModelScope.launch {
            try {
                val result = mRetrofitManger.create(BaseService::class.java)
                    .queryOrderDetails(id)
                disposeRetrofit(mOrderDetailsLiveData, result,true)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun cancelOrder(entity: RequestCancelOrderEntity) {
        viewModelScope.launch {
            try {
                val result = mRetrofitManger.create(BaseService::class.java)
                    .cancelOrder(entity)
                disposeRetrofit(mCancelOrderLiveData, result)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}