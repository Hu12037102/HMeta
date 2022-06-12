package com.sbnh.comm.base.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sbnh.comm.http.RetrofitManger

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/10 11:42
 * 更新时间: 2022/6/10 11:42
 * 描述:
 */
open class BaseViewModel : ViewModel() {
    protected val mRetrofitManger: RetrofitManger by lazy { RetrofitManger.Instance }
     val mToastLiveData: MutableLiveData<String> = MutableLiveData()
}