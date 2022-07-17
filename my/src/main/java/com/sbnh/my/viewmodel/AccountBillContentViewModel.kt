package com.sbnh.my.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sbnh.comm.base.viewmodel.BaseViewModel
import com.sbnh.comm.entity.base.BaseEntity
import com.sbnh.comm.entity.base.BasePagerEntity2
import com.sbnh.comm.entity.my.AccountBillEntity
import com.sbnh.comm.entity.request.RequestAccountBillEntity
import com.sbnh.my.MyService
import kotlinx.coroutines.launch

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/7/15 9:53
 * 更新时间: 2022/7/15 9:53
 * 描述:
 */
class AccountBillContentViewModel : BaseViewModel() {
    val mAccountBillLiveData =
        MutableLiveData<BaseEntity<BasePagerEntity2<List<AccountBillEntity>>>>()

    fun loadAccountBills(entity: RequestAccountBillEntity) {
        viewModelScope.launch {
            val result = try {
                mRetrofitManger.create(MyService::class.java)
                    .loadAccountBills(entity)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
            disposeRetrofit(mAccountBillLiveData, result, true)
        }

    }
}