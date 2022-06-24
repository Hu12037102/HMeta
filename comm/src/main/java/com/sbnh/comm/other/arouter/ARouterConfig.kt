package com.sbnh.comm.other.arouter

import androidx.fragment.app.Fragment

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/13 14:39
 * 更新时间: 2022/6/13 14:39
 * 描述:
 */
class ARouterConfig {
    object Key {
        const val HAS_LOGIN = "has_login"
        const val WEB_URL = "web_url"
        const val ID = "id"
        const val PARCELABLE="parcelable"

    }

    class Path {
        object Main {
            const val ACTIVITY_MAIN = "/main/activity/main"
        }

        object Home {
            const val FRAGMENT_HOME = "/home/fragment/home"
            const val ACTIVITY_COLLECTION_DETAILS = "/home/activity/collection/details"

        }

        object Bazaar {
            const val FRAGMENT_BAZAAR = "/bazaar/fragment/bazaar"
        }

        object My {
            const val FRAGMENT_MY = "/my/fragment/my"
            const val FRAGMENT_MY_COLLECTION = "/my/fragment/collection"
            const val ACTIVITY_SETTING = "/my/activity/setting"
            const val ACTIVITY_MY_ACCOUNT_INFO = "/my/activity/my/account/info"
            const val ACTIVITY_SET_PAYMENT_PASSWORD = "/my/activity/set/payment/password"
            const val ACTIVITY_GIVE_COLLECTION = "/my/activity/give/collection"
            const val ACTIVITY_MY_ORDER_LIST = "/my/activity/my/order/list"
            const val FRAGMENT_MY_ORDER_CONTENT="/my/fragment/my/order/content"
            const val ACTIVITY_OFFICIAL_ACCOUNTS="/my/activity/official/account"
        }

        object Login {
            const val ACTIVITY_LOGIN = "/login/activity/${Value.LOGIN_ACTIVITY}"
            const val ACTIVITY_REGISTER = "/login/activity/${Value.REGISTER_ACTIVITY}"
        }

        object Pay {
            const val ACTIVITY_BANK_CARD_LIST = "/pay/activity/bank/card/list"
            const val ACTIVITY_ADD_BANK_CARD = "/pay/activity/add/bank/card"
            const val DIALOG_SELECTOR_BANK_CARD="/pay/dialog/selector/bank/card"
        }

        object Comm {
            const val ACTIVITY_WEB_CONTENT = "/comm/activity/web/content"
        }

        object Order {
            const val ACTIVITY_ORDER_DETAILS = "/order/activity/order/details"
        }
    }

    object Value {
        const val LOGIN_ACTIVITY = "login"
        const val REGISTER_ACTIVITY = "register"
    }
}