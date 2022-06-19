package com.sbnh.my.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sbnh.comm.base.viewmodel.BaseViewModel
import com.sbnh.my.MyService
import kotlinx.coroutines.launch

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/16 15:42
 * 更新时间: 2022/6/16 15:42
 * 描述:我的账号详情ViewModel
 */
class MyAccountInfoViewModel : BaseViewModel() {
    val mExitLoginLiveData = MutableLiveData<Unit>()
    fun exitLoginService() {
        viewModelScope.launch {
            val result = mRetrofitManger.create(MyService::class.java)
                .exitLoginService()
            disposeRetrofit(mExitLoginLiveData, result)
        }
    }
}