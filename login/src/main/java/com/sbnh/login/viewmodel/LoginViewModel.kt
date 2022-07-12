package com.sbnh.login.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sbnh.comm.base.viewmodel.TimerViewModel
import com.sbnh.comm.entity.base.UserInfoEntity
import com.sbnh.comm.entity.request.RequestLoginEntity
import com.sbnh.comm.info.UserInfoStore
import com.sbnh.login.LoginService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/14 19:52
 * 更新时间: 2022/6/14 19:52
 * 描述:
 */
class LoginViewModel : TimerViewModel() {
    val mLoginLiveData = MutableLiveData<UserInfoEntity>()
    fun login(requestLoginEntity: RequestLoginEntity) {

        viewModelScope.launch(Dispatchers.Main) {
            val response = try {
                showLoading()
                mRetrofitManger.create(LoginService::class.java)
                    .login(requestLoginEntity)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            } finally {
                dismissLoading()
            }
            disposeRetrofit(mLoginLiveData, response)
        }
    }

}