package com.sbnh.comm.entity.my

data class MyCollectionEntity(var id: String? = "") {

    /**
     * 商品拥有数量
     */
    var count: Int? = 0

    /**
     * 头像
     */
    var header: String? = ""

    /**
     * 商品id
     */
    var merchandiseId: String? = ""

    /**
     * 商品名称
     */
    var merchandiseName: String? = ""

    /**
     * 用户昵称
     */
    var nickname: String? = ""

    /**
     * 资源url
     */
    var resourceUrl: String? = ""

}
