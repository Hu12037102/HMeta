package com.sbnh.comm.entity.base

import androidx.annotation.DrawableRes

data class SelectorTabEntity(
    var name: String = "",
    var isSelector: Boolean = false,
    @DrawableRes var normalRes: Int = 0,
    @DrawableRes var selectorRes: Int = 0,
    val type: Int? = null

) {
    class Bazaar {
        companion object {
            //寄售
            const val TYPE_BAZAAR = 1

            //已售出
            const val TYPE_BEEN_SOLD = 2
        }

    }

    class AccountBill {
        companion object {
            //全部
            const val TYPE_ALL = 0

            //充值
            const val TYPE_TOP_UP = 1

            //提现
            const val TYPE_WITHDRAW = 2

            //购买（支出）
            const val TYPE_EXPEND = 3

            //收入
            const val TYPE_REVENUES = 4
        }
    }

    class My {
        companion object {
            //我的藏品
            const val IN_MY_COLLECTION = 0

            //上架中
            const val IN_THE_SHELF = 1

            //已售出
            const val IN_THE_SOLD = 2
        }
    }

}
