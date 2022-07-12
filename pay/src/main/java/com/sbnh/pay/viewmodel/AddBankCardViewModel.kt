package com.sbnh.pay.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sbnh.comm.base.viewmodel.TimerViewModel
import com.sbnh.comm.entity.base.BaseEntity
import com.sbnh.comm.entity.pay.NumberQueryBankCardInfoEntity
import com.sbnh.comm.entity.request.RequestBankCardInfoEntity
import com.sbnh.comm.entity.request.RequestBindingBankCardAfterEntity
import com.sbnh.comm.entity.request.RequestBindingBankCardBeforeEntity
import com.sbnh.pay.PayService
import kotlinx.coroutines.launch
import retrofit2.Response

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/17 11:58
 * 更新时间: 2022/6/17 11:58
 * 描述:添加银行卡
 */
class AddBankCardViewModel : TimerViewModel() {
    val mQueryBankCardLiveData = MutableLiveData<BaseEntity<NumberQueryBankCardInfoEntity>>()
    val mBindingBankCardBeforeLiveData = MutableLiveData<BaseEntity<String>>()
    val mBindingBankCardAfterLiveData = MutableLiveData<BaseEntity<Unit>>()
    fun queryBankCardInfo(
        entity: RequestBankCardInfoEntity,
        isBindingBankCardBefore: Boolean = false
    ) {
        viewModelScope.launch {
            var result: Response<BaseEntity<NumberQueryBankCardInfoEntity>>?
            try {
                result = mRetrofitManger.create(PayService::class.java)
                    .queryBankCardInfo(entity)
                if (result.body() is BaseEntity<NumberQueryBankCardInfoEntity>) {
                    result.body()?.data?.isBindingBankCardBefore = isBindingBankCardBefore
                }

            } catch (e: Exception) {
                e.printStackTrace()
                result = null
            }
            disposeRetrofit(mQueryBankCardLiveData, result)
        }
    }

    fun bindingBankCardBefore(entity: RequestBindingBankCardBeforeEntity) {
        viewModelScope.launch {
            val result = try {
                mRetrofitManger.create(PayService::class.java)
                    .bindingBankCardBefore(entity)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
            disposeRetrofit(mBindingBankCardBeforeLiveData, result)
        }
    }

    fun bindingBankCardAfter(entity: RequestBindingBankCardAfterEntity) {
        viewModelScope.launch {
            val result = try {
                mRetrofitManger.create(PayService::class.java)
                    .bindingBankCardAfter(entity)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
            disposeRetrofit(mBindingBankCardAfterLiveData, result)
        }
    }

}