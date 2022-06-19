package com.sbnh.login

import com.sbnh.comm.entity.base.UserInfoEntity
import com.sbnh.comm.entity.request.RequestLoginEntity
import com.sbnh.comm.entity.request.RequestRegisterEntity
import com.sbnh.comm.http.IApiService
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/18 11:19
 * 更新时间: 2022/6/18 11:19
 * 描述:
 */
interface LoginService {
    @POST(IApiService.Path.USER_LOGIN)
    suspend fun login(@Body loginEntity: RequestLoginEntity): Response<UserInfoEntity>

    @POST(IApiService.Path.REGISTER)
    suspend fun register(@Body registerEntity: RequestRegisterEntity): Response<UserInfoEntity>
}