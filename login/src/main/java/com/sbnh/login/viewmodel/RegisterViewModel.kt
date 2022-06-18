package com.sbnh.login.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sbnh.comm.base.viewmodel.TimerViewModel
import com.sbnh.comm.entity.base.UserInfoEntity
import com.sbnh.comm.entity.request.RequestRegisterEntity
import com.sbnh.login.LoginService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/15 14:48
 * 更新时间: 2022/6/15 14:48
 * 描述:
 */
class RegisterViewModel : TimerViewModel() {
    val mRegisterLiveData = MutableLiveData<UserInfoEntity>()
    fun register(registerEntity: RequestRegisterEntity) {

        viewModelScope.launch {
            try {
                val result = mRetrofitManger.create(LoginService::class.java)
                    .register(registerEntity)
                disposeRetrofit(mRegisterLiveData, result)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }


    }
}