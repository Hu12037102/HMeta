package com.sbnh.bazaar

import com.sbnh.comm.entity.base.BasePagerEntity
import com.sbnh.comm.entity.bazaar.BazaarEntity
import com.sbnh.comm.entity.bazaar.BazaarTabEntity
import com.sbnh.comm.entity.request.RequestBazaarEntity
import com.sbnh.comm.http.IApiService
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/7/12 11:42
 * 更新时间: 2022/7/12 11:42
 * 描述:
 */
interface BazaarService {

    @GET(IApiService.Path.LOAD_BAZAAR_TABS)
    suspend fun loadBazaarTabs(): Response<List<BazaarTabEntity>>

    @POST(IApiService.Path.LOAD_BAZAAR_CONTENT_LIST)
    suspend fun loadBazaarContentList(@Body entity: RequestBazaarEntity):Response<BasePagerEntity<List<BazaarEntity>>>
}