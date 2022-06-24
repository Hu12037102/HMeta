package com.sbnh.my.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sbnh.comm.base.viewmodel.BaseViewModel
import com.sbnh.comm.entity.base.BasePagerEntity
import com.sbnh.comm.entity.my.MyCollectionEntity
import com.sbnh.comm.entity.request.RequestPagerListEntity
import com.sbnh.my.MyService
import kotlinx.coroutines.launch

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/14 16:58
 * 更新时间: 2022/6/14 16:58
 * 描述:我的藏品ViewModel
 */
class MyCollectionViewModel : BaseViewModel() {

    val mCollectionLiveData = MutableLiveData<BasePagerEntity<List<MyCollectionEntity>>>()

    fun loadCollectionList(requestPagerListEntity: RequestPagerListEntity) {
        viewModelScope.launch {
            try {
//                val result = mRetrofitManger.create(MyService::class.java)
//                    .loadMyCollectionList(requestPagerListEntity)
//                disposeRetrofit(mCollectionLiveData, result)

                mCollectionLiveData.value = BasePagerEntity(listOf(
                    MyCollectionEntity(
                        "974d234d70de40c9a1bac3c6824c4e73",
                        2,
                        "https://prod.cdn.sbnh.cn/healer_nft_head_16533711673.jpg",
                        "974d234d70de40c9a1bac3c6824c4e73",
                        "极光绿 银河白-黄金",
                        "收藏家k43bUU",
                        "https://prod.cdn.sbnh.cn/GoldGreen.png"),
                    MyCollectionEntity(
                        "32538edd06874fda8024e2580355db2a",
                        5,
                        "https://prod.cdn.sbnh.cn/healer_nft_head_16533711673.jpg",
                        "32538edd06874fda8024e2580355db2a",
                        "星际黑-钻石",
                        "收藏家k43bUU",
                        "https://prod.cdn.sbnh.cn/DiamondsBlack.png"),
                    MyCollectionEntity(
                        "974d234d70de40c9a1bac3c6824c4e73",
                        2,
                        "https://prod.cdn.sbnh.cn/healer_nft_head_16533711673.jpg",
                        "974d234d70de40c9a1bac3c6824c4e73",
                        "极光绿 银河白-黄金",
                        "收藏家k43bUU",
                        "https://prod.cdn.sbnh.cn/GoldGreen.png"),
                    MyCollectionEntity(
                        "32538edd06874fda8024e2580355db2a",
                        5,
                        "https://prod.cdn.sbnh.cn/healer_nft_head_16533711673.jpg",
                        "32538edd06874fda8024e2580355db2a",
                        "星际黑-钻石",
                        "收藏家k43bUU",
                        "https://prod.cdn.sbnh.cn/DiamondsBlack.png"),
                    MyCollectionEntity(
                        "974d234d70de40c9a1bac3c6824c4e73",
                        2,
                        "https://prod.cdn.sbnh.cn/healer_nft_head_16533711673.jpg",
                        "974d234d70de40c9a1bac3c6824c4e73",
                        "极光绿 银河白-黄金",
                        "收藏家k43bUU",
                        "https://prod.cdn.sbnh.cn/GoldGreen.png"),
                    MyCollectionEntity(
                        "32538edd06874fda8024e2580355db2a",
                        5,
                        "https://prod.cdn.sbnh.cn/healer_nft_head_16533711673.jpg",
                        "32538edd06874fda8024e2580355db2a",
                        "星际黑-钻石",
                        "收藏家k43bUU",
                        "https://prod.cdn.sbnh.cn/DiamondsBlack.png"),
                    MyCollectionEntity(
                        "974d234d70de40c9a1bac3c6824c4e73",
                        2,
                        "https://prod.cdn.sbnh.cn/healer_nft_head_16533711673.jpg",
                        "974d234d70de40c9a1bac3c6824c4e73",
                        "极光绿 银河白-黄金",
                        "收藏家k43bUU",
                        "https://prod.cdn.sbnh.cn/GoldGreen.png"),
                    MyCollectionEntity(
                        "32538edd06874fda8024e2580355db2a",
                        5,
                        "https://prod.cdn.sbnh.cn/healer_nft_head_16533711673.jpg",
                        "32538edd06874fda8024e2580355db2a",
                        "星际黑-钻石",
                        "收藏家k43bUU",
                        "https://prod.cdn.sbnh.cn/DiamondsBlack.png"),
                    MyCollectionEntity(
                        "974d234d70de40c9a1bac3c6824c4e73",
                        2,
                        "https://prod.cdn.sbnh.cn/healer_nft_head_16533711673.jpg",
                        "974d234d70de40c9a1bac3c6824c4e73",
                        "极光绿 银河白-黄金",
                        "收藏家k43bUU",
                        "https://prod.cdn.sbnh.cn/GoldGreen.png"),
                    MyCollectionEntity(
                        "32538edd06874fda8024e2580355db2a",
                        5,
                        "https://prod.cdn.sbnh.cn/healer_nft_head_16533711673.jpg",
                        "32538edd06874fda8024e2580355db2a",
                        "星际黑-钻石",
                        "收藏家k43bUU",
                        "https://prod.cdn.sbnh.cn/DiamondsBlack.png"),
                ))

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}