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
import retrofit2.Response

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/21 14:13
 * 更新时间: 2022/6/21 14:13
 * 描述:
 */
open class BaseOrderViewModel : TimerViewModel() {
    val mCommitOrderLiveData: MutableLiveData<BaseEntity<ResultCommitOrderEntity>> by lazy { MutableLiveData() }
    val mOrderDetailsLiveData: MutableLiveData<BaseEntity<OrderEntity>> by lazy { MutableLiveData() }
    val mCancelOrderLiveData: MutableLiveData<BaseEntity<Boolean>> by lazy { MutableLiveData() }
    fun commitOrder(entity: RequestCreateOrderEntity) {
        viewModelScope.launch {
            val result = try {
                mRetrofitManger.create(BaseService::class.java)
                    .commitOrder(entity)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
            disposeRetrofit(mCommitOrderLiveData, result)
        }
    }

    fun queryOrderDetails(id: String) {
        viewModelScope.launch {
            val result = try {
                mRetrofitManger.create(BaseService::class.java)
                    .queryOrderDetails(id)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
            disposeRetrofit(mOrderDetailsLiveData, result, true)
        }
    }

    /**
     * @param isAutoCancel  true 是统自动取消 false 用户主动操作
     */
    fun cancelOrder(entity: RequestCancelOrderEntity, isAutoCancel: Boolean= false) {
        viewModelScope.launch {
            var result: Response<BaseEntity<Boolean>>?
            try {
                result= mRetrofitManger.create(BaseService::class.java)
                    .cancelOrder(entity)
                result.body()?.data =isAutoCancel
            } catch (e: Exception) {
                e.printStackTrace()
                result= null
            }
            disposeRetrofit(mCancelOrderLiveData, result)
        }
    }
}