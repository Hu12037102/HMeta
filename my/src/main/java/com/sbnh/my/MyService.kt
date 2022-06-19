package com.sbnh.my

import com.sbnh.comm.http.IApiService
import retrofit2.Response
import retrofit2.http.GET

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/19 18:41
 * 更新时间: 2022/6/19 18:41
 * 描述:
 */
interface MyService {
    @GET(IApiService.Path.EXIT_LOGIN)
    suspend fun exitLoginService(): Response<Unit>

}