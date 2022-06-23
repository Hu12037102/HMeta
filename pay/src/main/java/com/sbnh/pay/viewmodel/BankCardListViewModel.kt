package com.sbnh.pay.viewmodel

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sbnh.comm.base.activity.BaseCompatActivity
import com.sbnh.comm.base.viewmodel.BaseViewModel
import com.sbnh.comm.entity.base.BaseEntity
import com.sbnh.comm.entity.base.BasePagerEntity2
import com.sbnh.comm.entity.pay.BankCardEntity
import com.sbnh.comm.entity.request.RequestBasePagerEntity
import com.sbnh.pay.PayService
import kotlinx.coroutines.launch

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/17 9:51
 * 更新时间: 2022/6/17 9:51
 * 描述:
 */
class BankCardListViewModel : BaseViewModel() {
    val mBankListLiveData = MutableLiveData<BaseEntity<BasePagerEntity2<List<BankCardEntity>>>>()
    fun loadBankCardList(entity: RequestBasePagerEntity) {
        viewModelScope.launch {
            try {
                val result = mRetrofitManger.create(PayService::class.java)
                    .queryBankCardList(entity)
                disposeRetrofit(mBankListLiveData, result)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}