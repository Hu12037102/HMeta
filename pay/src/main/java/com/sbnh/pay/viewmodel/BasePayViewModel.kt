package com.sbnh.pay.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sbnh.comm.base.viewmodel.BaseViewModel
import com.sbnh.comm.entity.base.BaseEntity
import com.sbnh.comm.entity.base.BasePagerEntity2
import com.sbnh.comm.entity.pay.BankCardEntity
import com.sbnh.comm.entity.request.RequestPagerListEntity
import com.sbnh.pay.PayService
import kotlinx.coroutines.launch

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/24 13:48
 * 更新时间: 2022/6/24 13:48
 * 描述:
 */
open class BasePayViewModel : BaseViewModel() {
    val mBankListLiveData = MutableLiveData<BaseEntity<BasePagerEntity2<List<BankCardEntity>>>>()
    fun loadBankCardList(entity: RequestPagerListEntity) {
        viewModelScope.launch {
            val result = try {
                mRetrofitManger.create(PayService::class.java)
                    .queryBankCardList(entity)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
            disposeRetrofit(mBankListLiveData, result)
        }
    }
}