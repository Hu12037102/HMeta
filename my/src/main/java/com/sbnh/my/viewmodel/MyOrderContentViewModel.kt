package com.sbnh.my.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sbnh.comm.base.viewmodel.BaseViewModel
import com.sbnh.comm.entity.base.BaseEntity
import com.sbnh.comm.entity.base.BasePagerEntity2
import com.sbnh.comm.entity.order.OrderEntity
import com.sbnh.comm.entity.order.RequestOrderListEntity
import com.sbnh.my.MyService
import kotlinx.coroutines.launch

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/22 12:15
 * 更新时间: 2022/6/22 12:15
 * 描述:
 */
class MyOrderContentViewModel : BaseViewModel() {
    val mOrderListLiveData = MutableLiveData<BaseEntity<BasePagerEntity2<List<OrderEntity>>>>()
    fun loadMyOrderList(entity: RequestOrderListEntity) {
        viewModelScope.launch {
            try {
                val result = mRetrofitManger.create(MyService::class.java)
                    .queryMyLoadList(entity)
                disposeRetrofit(mOrderListLiveData, result)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}