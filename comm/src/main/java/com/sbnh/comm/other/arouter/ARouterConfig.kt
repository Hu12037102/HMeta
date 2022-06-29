package com.sbnh.comm.other.arouter

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/13 14:39
 * 更新时间: 2022/6/13 14:39
 * 描述:
 */
class ARouterConfig {
    object Key {
        const val WEB_URL = "web_url"
        const val ID = "id"
        const val MY_COLLECTION = "my_collection"
        const val PARCELABLE="parcelable"
        const val PICTURE_PATH="picturePath"
        const val CONTENT="content"

        /**
         * 收藏id
         */
        const val CID = "cid"

    }

    class Path {
        object Main {
            const val ACTIVITY_MAIN = "/main/activity/main"
        }

        object Home {
            const val FRAGMENT_HOME = "/home/fragment/home"
            const val ACTIVITY_COLLECTION_DETAILS = "/home/activity/collection/details"
            const val ACTIVITY_SPLASH = "/home/activity/splash"

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
            const val ACTIVITY_PICTURE_SAVE="/my/activity/picture/save"
            const val ACTIVITY_COLLECTION_NUM_DETAILS = "/my/activity/collection/num/details"
            const val FRAGMENT_COLLECTION_NUM_DETAILS = "/my/fragment/collection/num/details"
            const val ACTIVITY_GIVE_COLLECTION_LIST = "/my/activity/give/collection/list"
            const val FRAGMENT_GIVE_COLLECTION_CONTENT = "/my/activity/give/collection/content"
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
            const val DIALOG_INPUT_MESSAGE_CODE="/comm/dialog/input/message/code"
            const val DIALOG_VERSION_UPDATE="/comm/dialog/version/update"
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