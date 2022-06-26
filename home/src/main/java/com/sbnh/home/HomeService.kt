package com.sbnh.home

import com.sbnh.comm.entity.base.BasePagerEntity
import com.sbnh.comm.entity.home.CollectionEntity
import com.sbnh.comm.entity.home.HomeBannerEntity
import com.sbnh.comm.entity.request.RequestPagerListEntity
import com.sbnh.comm.http.IApiService
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/20 14:30
 * 更新时间: 2022/6/20 14:30
 * 描述:
 */
interface HomeService {
    @POST(IApiService.Path.HOME_PAGER_LIST)
    suspend fun loadCollectionList(@Body requestPagerList: RequestPagerListEntity): Response<BasePagerEntity<List<CollectionEntity>>>

    @GET(IApiService.Path.COLLECTION_DETAILS)
    suspend fun loadCollectionDetails(@Path(IApiService.Key.ID) path: String)  : Response<CollectionEntity>
    @GET(IApiService.Path.HOME_BANNER)
    suspend fun loadHomeBanner():Response<List<HomeBannerEntity>>

    @GET(IApiService.Path.KNAPSACK_COLLECTION_DETAILS)
    suspend fun loadKnapsackCollectionDetails(@Path(IApiService.Key.CID) cid: String, @Path(IApiService.Key.MERCHANDISE_ID) merchandiseId: String)  : Response<CollectionEntity>
}