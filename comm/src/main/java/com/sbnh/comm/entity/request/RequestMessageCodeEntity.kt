package com.sbnh.comm.entity.request

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/18 16:22
 * 更新时间: 2022/6/18 16:22
 * 描述:
 */
data class RequestMessageCodeEntity(
    val mobile: String,
   // val businessId: String? = "",
  //  val imei: String? = "",
   // val macAddress: String? = "",
   // val needGetCaptchaTime: Int? = 0,
    val randstr: String? = "",
   // var sceneId: String? = "",
    var ticket: String? = "",
   // var userIp: String? = ""
)
