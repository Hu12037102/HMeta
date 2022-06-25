package com.sbnh.comm.entity.pay

import android.os.Parcelable
import androidx.versionedparcelable.ParcelField
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/17 10:47
 * 更新时间: 2022/6/17 10:47
 * 描述:
 */
@Parcelize
data class BankCardEntity(
    var id: String? = "",
    var bankCode: String? = "",
    var bankName: String? = "",
    var bindId: String? = "",
    var cardNum: String? = "",
    var ccv: String? = "",
    var idCard: String? = "",
    var logo: String? = "",
    var mobile: String? = "",
    var backgroundColor: String? = "",
    var isCheck: Boolean = false
) : Parcelable
