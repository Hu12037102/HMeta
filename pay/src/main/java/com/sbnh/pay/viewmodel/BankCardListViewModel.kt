package com.sbnh.pay.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sbnh.comm.base.viewmodel.BaseViewModel
import com.sbnh.comm.entity.base.BaseEntity
import com.sbnh.comm.entity.base.BasePagerEntity2
import com.sbnh.comm.entity.pay.BankCardEntity
import com.sbnh.comm.entity.pay.RequestUnbindBankCardEntity
import com.sbnh.comm.entity.request.RequestPagerListEntity
import com.sbnh.pay.PayService
import kotlinx.coroutines.launch
import retrofit2.Response

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/17 9:51
 * 更新时间: 2022/6/17 9:51
 * 描述:
 */
class BankCardListViewModel : BasePayViewModel() {

    val mUnbindBankCardLiveData = MutableLiveData<BaseEntity<String>>()
    fun unbindBanCard(entity: RequestUnbindBankCardEntity) {
        viewModelScope.launch {
            var result: Response<BaseEntity<String>>?
            try {
                result = mRetrofitManger.create(PayService::class.java)
                    .unbindBankCard(entity)
                result.body()?.data = entity.id
            } catch (e: Exception) {
                e.printStackTrace()
                result = null
            }
            disposeRetrofit(mUnbindBankCardLiveData, result)
        }
    }
}