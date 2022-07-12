package com.sbnh.my.viewmodel

import com.sbnh.comm.base.viewmodel.BaseViewModel
import com.sbnh.comm.compat.DataCompat
import com.sbnh.comm.entity.base.*

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
                com.sbnh.comm.R.mipmap.icon_my_tab_order,
                TAB_ORDER
            )
        )
        list.add(
            TabEntity(
                DataCompat.getResString(com.sbnh.comm.R.string.official_accounts),
                com.sbnh.comm.R.mipmap.icon_my_tab_offcial_accounts,
                TAB_OFFICIAL_ACCOUNTS,
                false
            )
        )
        list.add(
            TabEntity(
                DataCompat.getResString(com.sbnh.comm.R.string.invite_friends),
                com.sbnh.comm.R.mipmap.icon_my_tab_invite_friends,
                TAB_INVITE_FRIEND
            )
        )
        list.add(
            TabEntity(
                DataCompat.getResString(com.sbnh.comm.R.string.examples_record),
                com.sbnh.comm.R.mipmap.icon_my_tab_examples_record,
                TAB_DONATION
            )
        )
        list.add(
            TabEntity(
                DataCompat.getResString(com.sbnh.comm.R.string.lottery_assistant),
                com.sbnh.comm.R.mipmap.icon_my_tab_lottery_assistant,
                TAB_LOTTERY_HELP, true
            )
        )
        list.add(
            TabEntity(
                DataCompat.getResString(com.sbnh.comm.R.string.my_wallet),
                com.sbnh.comm.R.mipmap.icon_my_tab_my_wallet,
                TAB_MY_WALLET, true
            )
        )
        list.add(
            TabEntity(
                DataCompat.getResString(com.sbnh.comm.R.string.compound),
                com.sbnh.comm.R.mipmap.icon_my_tab_compound,
                TAB_COMPOUND,
                true
            )
        )
        return list
    }

}