package com.sbnh.my.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sbnh.comm.base.viewmodel.BaseViewModel
import com.sbnh.comm.base.viewmodel.TimerViewModel
import com.sbnh.comm.entity.request.RequestSetPaymentPasswordEntity
import com.sbnh.my.MyService
import kotlinx.coroutines.launch

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/16 17:20
 * 更新时间: 2022/6/16 17:20
 * 描述:设置支付密码ViewBinding
 */
class SetPaymentPasswordViewModel : TimerViewModel() {
    val mPaymentPasswordLiveData: MutableLiveData<Unit> = MutableLiveData()
    fun setPaymentPassword(entity: RequestSetPaymentPasswordEntity) {
        viewModelScope.launch {
            val result = try {
                mRetrofitManger.create(MyService::class.java)
                    .setPaymentPassword(entity)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
            disposeRetrofit(mPaymentPasswordLiveData, result)
        }
    }
}