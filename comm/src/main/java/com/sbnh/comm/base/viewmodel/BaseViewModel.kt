package com.sbnh.comm.base.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sbnh.comm.entity.base.UserInfoEntity
import com.sbnh.comm.http.RetrofitManger
import com.sbnh.comm.info.UserInfoStore
import com.sbnh.comm.manger.ActivityCompatManger
import com.sbnh.comm.other.arouter.ARoutersActivity
import com.sbnh.comm.utils.LogUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/10 11:42
 * 更新时间: 2022/6/10 11:42
 * 描述:
 */
open class BaseViewModel : ViewModel() {
    companion object {
        const val TAG = "BaseViewModel"
    }

    protected val mRetrofitManger: RetrofitManger by lazy { RetrofitManger.Instance }
    val mToastLiveData: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val mUserInfoLiveData: MutableLiveData<UserInfoEntity> by lazy { MutableLiveData<UserInfoEntity>() }


    fun loadUserInfo() {
        viewModelScope.launch(Dispatchers.Main) {
            val entity = UserInfoStore.get().getEntity()
            LogUtils.w(TAG, "$entity")
            // mUserInfoLiveData.value = entity
            mUserInfoLiveData.value = entity
        }
    }

    fun exitLogin() {
        viewModelScope.launch(Dispatchers.Main) {
            UserInfoStore.get().clear()
            ActivityCompatManger.get().clear()
            ARoutersActivity.startLoginActivity()
        }
    }


}