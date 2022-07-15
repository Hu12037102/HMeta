package com.sbnh.comm.entity.request

import com.sbnh.comm.Contract

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/21 13:47
 * 更新时间: 2022/6/21 13:47
 * 描述:
 */
/*//抢购
const val TYPE_PANIC_BUYING = 1

//2市场购买
const val TYPE_BAZAAR_BUY = 2

@IntDef(TYPE_PANIC_BUYING, TYPE_BAZAAR_BUY)
@Retention(AnnotationRetention.SOURCE)
annotation class BuyType*/
data class RequestCreateOrderEntity(
    var productId: String? = "",
    var marketId: String?,
    //  var userId: String? = "",
    var type: Int = Contract.PutOrderType.OFFICIAL
)
