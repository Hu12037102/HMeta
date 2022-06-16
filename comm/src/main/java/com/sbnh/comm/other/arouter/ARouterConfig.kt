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
    }

    class Path {
        object Main {
            const val ACTIVITY_MAIN = "/main/activity/main"
        }

        object Home {
            const val FRAGMENT_HOME = "/home/fragment/home"

        }

        object Bazaar {
            const val FRAGMENT_BAZAAR = "/bazaar/fragment/bazaar"
        }

        object My {
            const val FRAGMENT_MY = "/my/fragment/my"
            const val FRAGMENT_MY_COLLECTION = "/my/fragment/collection"
            const val ACTIVITY_SETTING = "/my/activity/setting"
            const val ACTIVITY_MY_ACCOUNT_INFO = "/my/activity/my/account/info"
            const val ACTIVITY_SET_PAYMENT_PASSWORD="/my/activity/set/payment/password"
        }

        object Login {
            const val ACTIVITY_LOGIN = "/login/activity/login"
            const val ACTIVITY_REGISTER = "/login/activity/register"

        }

    }
}