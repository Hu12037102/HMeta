package com.sbnh.my.viewmodel

import com.sbnh.comm.base.viewmodel.BaseViewModel
import com.sbnh.comm.compat.DataCompat
import com.sbnh.comm.entity.base.TabEntity

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/13 16:35
 * 更新时间: 2022/6/13 16:35
 * 描述:
 */
class MyViewModel : BaseViewModel() {
    fun createTabs(): List<TabEntity> {
        val list = ArrayList<TabEntity>()
        list.add(
            TabEntity(
                DataCompat.getResString(com.sbnh.comm.R.string.my_order),
                com.sbnh.comm.R.mipmap.icon_my_tab_order
            )
        )
        list.add(
            TabEntity(
                DataCompat.getResString(com.sbnh.comm.R.string.official_accounts),
                com.sbnh.comm.R.mipmap.icon_my_tab_offcial_accounts
            )
        )
        list.add(
            TabEntity(
                DataCompat.getResString(com.sbnh.comm.R.string.invite_friends),
                com.sbnh.comm.R.mipmap.icon_my_tab_invite_friends
            )
        )
        list.add(
            TabEntity(
                DataCompat.getResString(com.sbnh.comm.R.string.examples_record),
                com.sbnh.comm.R.mipmap.icon_my_tab_examples_record
            )
        )
        list.add(
            TabEntity(
                DataCompat.getResString(com.sbnh.comm.R.string.lottery_assistant),
                com.sbnh.comm.R.mipmap.icon_my_tab_lottery_assistant
            )
        )
        list.add(
            TabEntity(
                DataCompat.getResString(com.sbnh.comm.R.string.my_wallet),
                com.sbnh.comm.R.mipmap.icon_my_tab_my_wallet
            )
        )
        list.add(
            TabEntity(
                DataCompat.getResString(com.sbnh.comm.R.string.compound),
                com.sbnh.comm.R.mipmap.icon_my_tab_compound
            )
        )
        return list
    }

}