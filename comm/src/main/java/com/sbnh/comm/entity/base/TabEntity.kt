package com.sbnh.comm.entity.base

import androidx.annotation.DrawableRes
import androidx.annotation.IntDef

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/14 15:46
 * 更新时间: 2022/6/14 15:46
 * 描述:
 */
//我的订单
const val TAB_ORDER = 100

//公众号
const val TAB_OFFICIAL_ACCOUNTS = 101

//邀请好友
const val TAB_INVITE_FRIEND = 102

//合成
const val TAB_COMPOUND = 103

//抽奖助手
const val TAB_LOTTERY_HELP = 104

//我的钱包
const val TAB_MY_WALLET = 105

@IntDef(
    TAB_ORDER,
    TAB_OFFICIAL_ACCOUNTS,
    TAB_INVITE_FRIEND,
    TAB_COMPOUND,
    TAB_LOTTERY_HELP,
    TAB_MY_WALLET
)
@Retention(AnnotationRetention.SOURCE)
annotation class MyTabId
data class TabEntity(
    var text: String = "",
    @DrawableRes var resDrawable: Int = 0,
    @MyTabId val id: Int,
    val checkLogin: Boolean = true
)
