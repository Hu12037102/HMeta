package com.sbnh.comm.entity.base

import androidx.annotation.DrawableRes

data class SelectorTabEntity(
    var name: String = "",
    var isSelector: Boolean = false,
    @DrawableRes var normalRes: Int = 0,
    @DrawableRes var selectorRes: Int = 0
)
