package com.sbnh.comm.entity.base

import androidx.annotation.DrawableRes

data class SelectorTabEntity(
    var name: String = "",
    var isCheck: Boolean = false,
    @DrawableRes var defaultRes: Int = 0,
    @DrawableRes var checkRes: Int = 0
)
