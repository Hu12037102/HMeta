package com.sbnh.pay.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sbnh.comm.base.viewmodel.BaseViewModel
import com.sbnh.comm.entity.base.BaseEntity
import com.sbnh.comm.entity.request.RequestWithdrawBankCardEntity
import com.sbnh.pay.PayService
import kotlinx.coroutines.launch

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/7/4 11:54
 * 更新时间: 2022/7/4 11:54
 * 描述:
 */
class WithdrawViewModel : BasePayViewModel() {
    val mWithdrawBankCardLiveData = MutableLiveData<BaseEntity<Unit>>()
    fun withdrawBankCard(entity: RequestWithdrawBankCardEntity) {
        viewModelScope.launch {
            val result = try {
                mRetrofitManger.create(PayService::class.java)
                    .withdrawBankCard(entity)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
            disposeRetrofit(mWithdrawBankCardLiveData, result)
        }

    }
}