package com.sbnh.pay

import com.sbnh.comm.entity.base.BaseEntity
import com.sbnh.comm.entity.pay.BankCardInfoEntity
import com.sbnh.comm.entity.request.RequestBankCardInfoEntity
import com.sbnh.comm.entity.request.RequestBindingBankCardAfterEntity
import com.sbnh.comm.entity.request.RequestBindingBankCardBeforeEntity
import com.sbnh.comm.http.IApiService
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/22 20:22
 * 更新时间: 2022/6/22 20:22
 * 描述:
 */
interface PayService {
    @POST(IApiService.Path.QUERY_BANK_CARD_INFO)
    suspend fun queryBankCardInfo(@Body entity: RequestBankCardInfoEntity): Response<BaseEntity<BankCardInfoEntity>>

    @POST(IApiService.Path.BINDING_BANK_CARD_BEFORE)
    suspend fun bindingBankCardBefore(@Body entity: RequestBindingBankCardBeforeEntity): Response<BaseEntity<String>>

    @POST(IApiService.Path.BINDING_BANK_CARD_AFTER)
    suspend fun bindingBankCardAfter(@Body entity: RequestBindingBankCardAfterEntity): Response<BaseEntity<Unit>>
}