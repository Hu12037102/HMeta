package com.sbnh.pay.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sbnh.comm.base.viewmodel.BaseViewModel
import com.sbnh.comm.base.viewmodel.TimerViewModel
import com.sbnh.comm.entity.base.BaseEntity
import com.sbnh.comm.entity.pay.BankCardInfoEntity
import com.sbnh.comm.entity.request.RequestBankCardInfoEntity
import com.sbnh.comm.entity.request.RequestBindingBankCardBeforeEntity
import com.sbnh.comm.entity.request.RequestMessageCodeEntity
import com.sbnh.pay.PayService
import kotlinx.coroutines.launch

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/17 11:58
 * 更新时间: 2022/6/17 11:58
 * 描述:添加银行卡
 */
class AddBankCardViewModel : TimerViewModel() {
    val mQueryBankCardLiveData = MutableLiveData<BaseEntity<BankCardInfoEntity>>()
    val mBindingBankCardBeforeLiveData = MutableLiveData<BaseEntity<String>>()
    fun queryBankCardInfo(entity: RequestBankCardInfoEntity) {
        viewModelScope.launch {
            try {
                val result = mRetrofitManger.create(PayService::class.java)
                    .queryBankCardInfo(entity)
                disposeRetrofit(mQueryBankCardLiveData, result)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun bindingBankCardBefore(entity: RequestBindingBankCardBeforeEntity) {
        viewModelScope.launch {
            try {
                val result = mRetrofitManger.create(PayService::class.java)
                    .bindingBankCardBefore(entity)
                disposeRetrofit(mBindingBankCardBeforeLiveData, result)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}