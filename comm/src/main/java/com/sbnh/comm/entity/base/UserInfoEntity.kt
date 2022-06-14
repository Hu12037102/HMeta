package com.sbnh.comm.entity.base

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/14 10:40
 * 更新时间: 2022/6/14 10:40
 * 描述:
 */
data class UserInfoEntity(var id: String? = "") {
    var address: String? = ""
    var hasPayPassword: Boolean? = false
    var hasRealName: Boolean? = false
    var header: String? = ""
    var inviteCode: String? = ""
    var mobile: String? = ""
    var nickName: String? = ""
    var privateKey: String? = ""
    var sid: String? = ""

}
