package com.sbnh.order

import com.sbnh.comm.entity.base.BaseEntity
import com.sbnh.comm.entity.my.MyWalletEntity
import com.sbnh.comm.entity.request.RequestWalletPayOrderEntity
import com.sbnh.comm.http.IApiService
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/7/15 17:06
 * 更新时间: 2022/7/15 17:06
 * 描述:
 */
interface OrderService {

    @POST(IApiService.Path.WALLET_PAY_ORDER)
    suspend fun walletPayOrder(@Body entity:RequestWalletPayOrderEntity):Response<BaseEntity<MyWalletEntity>>
}