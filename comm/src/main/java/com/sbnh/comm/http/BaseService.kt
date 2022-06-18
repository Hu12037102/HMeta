package com.sbnh.comm.http

import com.sbnh.comm.config.AppConfig
import com.sbnh.comm.entity.request.RequestMessageCodeEntity
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/10 13:35
 * 更新时间: 2022/6/10 13:35
 * 描述:
 */
interface BaseService {
    @POST(ApiService.Path.GAIN_MESSAGE_CODE)
    suspend fun gainMessageCode(@Body entity: RequestMessageCodeEntity):Response<Unit>

}