package com.sbnh.order.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sbnh.comm.base.viewmodel.BaseOrderViewModel
import com.sbnh.comm.entity.base.BaseEntity
import com.sbnh.comm.entity.my.MyWalletEntity
import com.sbnh.comm.entity.pay.PayOrderBeforeResultEntity
import com.sbnh.comm.entity.request.RequestPayOrderAfterEntity
import com.sbnh.comm.entity.request.RequestPayOrderBeforeEntity
import com.sbnh.comm.entity.request.RequestWalletPayOrderEntity
import com.sbnh.comm.http.BaseService
import com.sbnh.order.OrderService
import kotlinx.coroutines.launch

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/21 9:22
 * 更新时间: 2022/6/21 9:22
 * 描述:
 */
class OrderDetailsViewModel : BaseOrderViewModel() {

    val mBeforePayLiveData = MutableLiveData<BaseEntity<PayOrderBeforeResultEntity>>()
    val mAfterPayLiveData = MutableLiveData<BaseEntity<Unit>>()
    val mWalletPayLiveData = MutableLiveData<BaseEntity<MyWalletEntity>>()
    fun payOrderBefore(entity: RequestPayOrderBeforeEntity) {
        viewModelScope.launch {
            val result = try {
                mRetrofitManger.create(BaseService::class.java)
                    .payOrderBefore(entity)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
            disposeRetrofit(mBeforePayLiveData, result)
        }
    }

    fun payOrderAfter(entity: RequestPayOrderAfterEntity) {
        viewModelScope.launch {
            val result = try {
                mRetrofitManger.create(BaseService::class.java)
                    .payOrderAfter(entity)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
            disposeRetrofit(mAfterPayLiveData, result)
        }
    }

    fun walletPayOrder(entity: RequestWalletPayOrderEntity) {
        viewModelScope.launch {
            val result = try {
                mRetrofitManger.create(OrderService::class.java)
                    .walletPayOrder(entity)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
            disposeRetrofit(mWalletPayLiveData, result)
        }

    }
}