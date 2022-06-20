package com.sbnh.comm.entity.home

import androidx.annotation.IntDef

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/20 14:40
 * 更新时间: 2022/6/20 14:40
 * 描述:
 */
//未开售
const val STATUS_UNSOLD = 0

//预售
const val STATUS_ADVANCE = 1

//售卖中
const val STATUS_ADVANCING = 2

//售完
const val STATUS_OUT = 3

@IntDef(STATUS_UNSOLD, STATUS_ADVANCE, STATUS_ADVANCING, STATUS_OUT)
@Retention(AnnotationRetention.SOURCE)
annotation class SaleStatus
data class CollectionEntity(var id: String? = "") {
    var chainType: String? = "" //（1：蚂蚁链 2天河链）
    var collectibleHeader: String? = ""
    var collectibleNickname: String? = ""
    var contractAddress: String? = ""
    var dynamicGraph: String? = ""
    var hasAirDrop: Boolean? = false
    var hasStart: Boolean? = false
    var header: String? = ""
    var merchandiseName: String? = ""
    var nickname: String? = ""
    var particulars: String? = ""
    var price: Double? = 0.0
    var remainQuantity: Int? = 0
    var resourceUrl: String? = ""

    @SaleStatus
    var saleStatus: Int? = STATUS_UNSOLD
    var saleTime: Long? = 0
    var tokenId: Long? = 0
    var totalQuantity: Long? = 0
    var transactionHash: String? = ""
    var uid: String? = ""

    companion object {
        @JvmStatic
        fun hasSaleTime(entity: CollectionEntity?) = (entity?.saleTime ?: 0) > System.currentTimeMillis()
    }
}
