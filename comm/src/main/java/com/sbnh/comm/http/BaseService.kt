package com.sbnh.comm.http

import com.sbnh.comm.entity.base.BaseEntity
import com.sbnh.comm.entity.base.BasePagerEntity
import com.sbnh.comm.entity.base.VersionEntity
import com.sbnh.comm.entity.my.MyCollectionEntity
import com.sbnh.comm.entity.my.MyWalletEntity
import com.sbnh.comm.entity.order.OrderEntity
import com.sbnh.comm.entity.order.ResultCommitOrderEntity
import com.sbnh.comm.entity.pay.PayOrderBeforeResultEntity
import com.sbnh.comm.entity.request.*
import retrofit2.Response
import retrofit2.http.*

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/10 13:35
 * 更新时间: 2022/6/10 13:35
 * 描述:
 */
interface BaseService {
    @POST(IApiService.Path.GAIN_MESSAGE_CODE)
    suspend fun gainMessageCode(@Body entity: RequestMessageCodeEntity): Response<Unit>

    @POST(IApiService.Path.REAL_NAME_AUTHENTICATION)
    suspend fun realNameAuthentication(@Body entity: RequestRealNameEmpty): Response<Unit>

    @POST(IApiService.Path.COMMIT_ORDER)
    suspend fun commitOrder(@Body entity: RequestCreateOrderEntity): Response<BaseEntity<ResultCommitOrderEntity>>

    @POST(IApiService.Path.QUERY_ORDER_DETAILS)
    suspend fun queryOrderDetails(@Path(IApiService.Key.ID) id: String): Response<BaseEntity<OrderEntity>>

    @POST(IApiService.Path.PAY_ORDER_BEFORE)
    suspend fun payOrderBefore(@Body entity: RequestPayOrderBeforeEntity): Response<BaseEntity<PayOrderBeforeResultEntity>>

    @POST(IApiService.Path.PAY_COLLECTION_AFTER)
    suspend fun payOrderAfter(@Body entity: RequestPayOrderAfterEntity): Response<BaseEntity<Unit>>

    @POST(IApiService.Path.CANCEL_ORDER)
    suspend fun cancelOrder(@Body entity: RequestCancelOrderEntity): Response<BaseEntity<Unit>>

    @GET(IApiService.Path.LOAD_APP_VERSION)
    suspend fun loadAppVersion(@Path(IApiService.Key.CODE) version: Long): Response<VersionEntity>

    @GET(IApiService.Path.QUERY_MY_WALLET)
    suspend fun queryMyWallet(): Response<BaseEntity<MyWalletEntity>>

    @POST(IApiService.Path.UP_COLLECTION)
    suspend fun upCollection(@Body entity: RequestUpCollectionEntity): Response<Unit>

    @GET(IApiService.Path.DOWN_COLLECTION)
    suspend fun downCollection(@Query(IApiService.Key.USER_COLLECTION_ID) userCollectionId: String): Response<Unit>

    @POST(IApiService.Path.LOAD_COLLECTION_TRANSACTIONS)
    suspend fun loadCollectionTransactions(@Body entity: RequestPagerTypeEntity):Response<BasePagerEntity<List<MyCollectionEntity>>>
}