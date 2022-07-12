package com.sbnh.comm.dialog.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sbnh.comm.entity.request.RequestRealNameEmpty
import com.sbnh.comm.http.BaseService
import com.sbnh.comm.viewmodel.BaseDialogViewModel
import kotlinx.coroutines.launch

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/19 13:49
 * 更新时间: 2022/6/19 13:49
 * 描述:
 */
class RealNameDialogViewModel : BaseDialogViewModel() {
    val mRealNameLiveData = MutableLiveData<Unit>()
    fun realNameAuthentication(entity: RequestRealNameEmpty) {
        viewModelScope.launch {
            val result = try {
                mRetrofitManger.create(BaseService::class.java).realNameAuthentication(entity)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
            disposeRetrofit(mRealNameLiveData, result)
        }
    }
}