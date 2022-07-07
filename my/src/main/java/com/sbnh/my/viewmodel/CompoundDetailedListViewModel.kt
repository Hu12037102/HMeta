package com.sbnh.my.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sbnh.comm.base.viewmodel.BaseViewModel
import com.sbnh.comm.entity.my.CompoundDetailedListEntity
import com.sbnh.my.MyService
import kotlinx.coroutines.launch

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/7/6 9:25
 * 更新时间: 2022/7/6 9:25
 * 描述:
 */
class CompoundDetailedListViewModel:BaseViewModel() {
    val mCompoundDetailedListLiveData = MutableLiveData<List<CompoundDetailedListEntity>>()
    fun loadCompoundDetailedList(){
        viewModelScope.launch {
            try {
               val result= mRetrofitManger.create(MyService::class.java)
                    .loadCompoundDetailedList()
                disposeRetrofit(mCompoundDetailedListLiveData,result,true)
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}