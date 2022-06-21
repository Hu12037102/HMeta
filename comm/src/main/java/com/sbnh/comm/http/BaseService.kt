package com.sbnh.comm.http

import com.google.gson.annotations.Until
import com.sbnh.comm.entity.base.BaseEntity
import com.sbnh.comm.entity.order.ResultCommitOrderEntity
import com.sbnh.comm.entity.request.RequestCreateOrderEntity
import com.sbnh.comm.entity.request.RequestMessageCodeEntity
import com.sbnh.comm.entity.request.RequestRealNameEmpty
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

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
    suspend fun queryOrderDetails(@Path(IApiService.Key.ID) id: String): Response<BaseEntity<Unit>>
}