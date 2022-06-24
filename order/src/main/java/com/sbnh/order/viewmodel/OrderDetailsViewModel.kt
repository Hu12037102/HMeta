package com.sbnh.order.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sbnh.comm.base.viewmodel.BaseOrderViewModel
import com.sbnh.comm.entity.base.BaseEntity
import com.sbnh.comm.entity.pay.PayOrderBeforeResultEntity
import com.sbnh.comm.entity.request.RequestPayOrderBeforeEntity
import com.sbnh.comm.http.BaseService
import kotlinx.coroutines.launch

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/21 9:22
 * 更新时间: 2022/6/21 9:22
 * 描述:
 */
class OrderDetailsViewModel : BaseOrderViewModel(){

    val mBeforePayLiveData = MutableLiveData<BaseEntity<PayOrderBeforeResultEntity>>()
    fun payOrderBefore(entity: RequestPayOrderBeforeEntity) {
        viewModelScope.launch {
            try {
                val result = mRetrofitManger.create(BaseService::class.java)
                    .payOrderBefore(entity)
                disposeRetrofit(mBeforePayLiveData, result)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}