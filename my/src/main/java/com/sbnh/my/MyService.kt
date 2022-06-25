package com.sbnh.my

import com.sbnh.comm.entity.base.BaseEntity
import com.sbnh.comm.entity.base.BasePagerEntity
import com.sbnh.comm.entity.base.BasePagerEntity2
import com.sbnh.comm.entity.my.CollectionNumDetailsEntity
import com.sbnh.comm.entity.my.GiveCollection
import com.sbnh.comm.entity.my.MyCollectionEntity
import com.sbnh.comm.entity.order.OrderEntity
import com.sbnh.comm.entity.order.RequestOrderListEntity
import com.sbnh.comm.entity.request.*
import com.sbnh.comm.http.IApiService
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/19 18:41
 * 更新时间: 2022/6/19 18:41
 * 描述:
 */
interface MyService {
    @GET(IApiService.Path.EXIT_LOGIN)
    suspend fun exitLoginService(): Response<Unit>

    @POST(IApiService.Path.QUERY_MY_ORDER_LIST)
    suspend fun queryMyLoadList(@Body entity: RequestOrderListEntity): Response<BaseEntity<BasePagerEntity2<List<OrderEntity>>>>

    @POST(IApiService.Path.SET_PAYMENT_PASSWORD)
    suspend fun setPaymentPassword(@Body entity: RequestSetPaymentPasswordEntity): Response<Unit>

    @POST(IApiService.Path.MY_COLLECTION_LIST)
    suspend fun loadMyCollectionList(@Body entity: RequestPagerListEntity): Response<BasePagerEntity<List<MyCollectionEntity>>>

    @POST(IApiService.Path.COLLECTION_NUM_DETAILS)
    suspend fun loadCollectionNumDetails(@Body entity: RequestCollectionNumDetailsEntity): Response<BasePagerEntity<List<CollectionNumDetailsEntity>>>

    @POST(IApiService.Path.GIVE_COLLECTION)
    suspend fun giveCollection(@Body entity: RequestGiveCollectionEntity): Response<Unit>

    @POST(IApiService.Path.GIVE_COLLECTION_LIST)
    suspend fun loadGiveCollectionList(@Body entity: RequestGiveCollectionListEntity): Response<BasePagerEntity<List<GiveCollection>>>

}