package com.sbnh.comm.entity.other

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/22 17:48
 * 更新时间: 2022/6/22 17:48
 * 描述:
 */
data class CaptchaCheckResultEntity(
    var ret: Int? = 0,
    var ticket: String? = "",
    var randstr: String? = ""
) {
    companion object {
        @JvmStatic
        fun isSucceed(entity: CaptchaCheckResultEntity?) = entity != null && entity.ret == 0
    }
}