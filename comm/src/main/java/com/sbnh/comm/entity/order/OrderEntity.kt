package com.sbnh.comm.entity.order

import androidx.annotation.IntDef

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/21 14:54
 * 更新时间: 2022/6/21 14:54
 * 描述:
 */
//待支付
const val STATUS_WAIT_PAY = 1

//已完成
const val STATUS_COMPLETE = 6

//已取消
const val STATUS_CANCEL = 8

//进行中(服务器等待第三方扣款回调，公司进行发货)
const val STATUS_PAY_CALLBACK = 10

@IntDef(STATUS_WAIT_PAY, STATUS_COMPLETE, STATUS_CANCEL, STATUS_PAY_CALLBACK)
@Retention(AnnotationRetention.SOURCE)
annotation class OrderStatus

data class OrderEntity(
    var id: String? = "",
    var orderNo: String? = "",
    var businessName: String? = "",
    var userId: String? = "",
    var businessUserId: String? = "",
    var businessId: String? = "",
    var coin: Double? = 0.0,
    var userCollectionId: String? = "",
    var token: String? = "",
    var createTime: Long? = 0,
    var storeId: String? = "",
    @OrderStatus
    var status: Int? = 0,
    var businessInfo: String? = "",
    var payType: Int? = 0,
    var startTime: Long? = 0,
    var endTime: Long? = 0,
    var desc: String? = "",
    var duration: Int? = 0,
    var remark: String? = "",
    var businessNickname: String? = "",
    var nickname: String? = "",
    var updateTime: Long? = 0,
    var isPreOrder: Int? = 0,
    var orderTimeOut: Long? = 0,
    var resourceUrl: String? = "",
)
