package com.sbnh.comm.entity.my

data class GiveCollectionTabEntity(
    @GiveType val id: Int? = null,
    val name: String = "",
    var isSelector: Boolean = false
)
