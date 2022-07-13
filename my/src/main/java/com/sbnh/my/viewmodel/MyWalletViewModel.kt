package com.sbnh.my.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sbnh.comm.base.viewmodel.BaseViewModel
import com.sbnh.comm.entity.base.BaseEntity
import com.sbnh.comm.entity.my.MyWalletEntity
import com.sbnh.my.MyService
import kotlinx.coroutines.launch

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/7/4 10:30
 * 更新时间: 2022/7/4 10:30
 * 描述:
 */
class MyWalletViewModel : BaseViewModel() {
    val mWalletLiveData = MutableLiveData<BaseEntity<MyWalletEntity>>()
    fun queryMyWallet() {
        viewModelScope.launch {
            val result = try {
                mRetrofitManger.create(MyService::class.java)
                    .queryMyWallet()
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
            disposeRetrofit(mWalletLiveData,result)
        }
    }

}