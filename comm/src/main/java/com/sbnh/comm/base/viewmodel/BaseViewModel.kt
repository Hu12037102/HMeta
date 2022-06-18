package com.sbnh.comm.base.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.sbnh.comm.compat.DataCompat
import com.sbnh.comm.entity.base.UserInfoEntity
import com.sbnh.comm.entity.request.RequestMessageCodeEntity
import com.sbnh.comm.http.ApiService
import com.sbnh.comm.http.BaseService
import com.sbnh.comm.http.ErrorResponse
import com.sbnh.comm.http.RetrofitManger
import com.sbnh.comm.info.UserInfoStore
import com.sbnh.comm.manger.ActivityCompatManger
import com.sbnh.comm.other.arouter.ARoutersActivity
import com.sbnh.comm.utils.LogUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response

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
    val mGainMessageCodeLiveData: MutableLiveData<Unit> by lazy { MutableLiveData<Unit>() }

    fun loadUserInfo() {
        viewModelScope.launch(Dispatchers.Main) {
            val result = UserInfoStore.get().getEntity()
            LogUtils.w(TAG, "$result")
            // mUserInfoLiveData.value = entity
            mUserInfoLiveData.value = result
        }
    }

    fun gainMessageCode(entity: RequestMessageCodeEntity) {
        viewModelScope.launch(Dispatchers.Main) {
            val result = mRetrofitManger.create(BaseService::class.java)
                .gainMessageCode(entity)
            disposeRetrofit(mGainMessageCodeLiveData, result)
        }
    }


    fun exitLogin() {
        viewModelScope.launch(Dispatchers.Main) {
            UserInfoStore.get().clear()
            ActivityCompatManger.get().clear()
            ARoutersActivity.startLoginActivity()
        }
    }

    fun <T> disposeRetrofit(liveData: MutableLiveData<T>?, response: Response<T>?){
        if (response == null) {
            mToastLiveData.value =
                DataCompat.getResString(com.sbnh.comm.R.string.default_http_error)
        } else {
            val message = response.message()
            LogUtils.w("disposeRetrofit", "$response---$message---")
            if (!DataCompat.isEmpty(response.message())) {
                mToastLiveData.value = message
            }
            if (response.isSuccessful) {
                liveData?.value = response.body()
                LogUtils.w("disposeRetrofit--", "成功")
            } else {
                when (response.code()) {
                    ApiService.HttpCode.CLIENT_ERROR -> {
                        LogUtils.w("disposeRetrofit--", "客户端异常")
                    }
                    ApiService.HttpCode.SERVICE_ERROR -> {
                        LogUtils.w("disposeRetrofit--", "服务器异常")
                    }
                    else -> {

                    }
                }
                try {
                    val errorBody = response.errorBody()
                    if (errorBody != null) {
                        val errorJson = errorBody.string()
                        val gson = Gson()
                        val errorEntity = gson.fromJson(errorJson, ErrorResponse::class.java)
                        mToastLiveData.value = errorEntity?.message
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

        }


    }

}