package com.sbnh.comm.entity.base

import androidx.annotation.DrawableRes

data class SelectorTabEntity(
    var name: String = "",
    var isSelector: Boolean = false,
    @DrawableRes var normalRes: Int = 0,
    @DrawableRes var selectorRes: Int = 0,
    val type: Int? = null

) {
    companion object {
        //寄售
        const val TYPE_BAZAAR = 1
        //已售出
        const val TYPE_BEEN_SOLD = 2
    }
}
