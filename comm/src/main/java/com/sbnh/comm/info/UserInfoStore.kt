package com.sbnh.comm.info

import androidx.datastore.preferences.core.MutablePreferences
import com.sbnh.comm.compat.DataCompat
import com.sbnh.comm.entity.base.UserInfoEntity
import com.sbnh.comm.room.DataStoreManger

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/14 10:38
 * 更新时间: 2022/6/14 10:38
 * 描述:
 */
class UserInfoStore private constructor() {
    companion object {
        const val KEY_ADDRESS = "key_address"
        const val KEY_HAS_PAY_PASSWORD = "key_has_pay_password"
        const val KEY_HAS_REAL_NAME = "key_has_real_name"
        const val KEY_HEADER = "key_header"
        const val KEY_ID = "key_user_id"
        const val KEY_INVITE_CODE = "key_invite_code"
        const val KEY_MOBILE = "key_mobile"
        const val KEY_NICK_NAME = "key_nick_name"
        const val KEY_PRIVATE_KEY = "key_private_key"
        const val KEY_SID = "key_sid"
        private val mInstance: UserInfoStore by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            UserInfoStore()
        }

        @JvmStatic
        fun get(): UserInfoStore {
            return mInstance
        }

        @JvmStatic
        fun isLogin(userInfoEntity: UserInfoEntity?): Boolean {
            return userInfoEntity != null && DataCompat.notEmpty(userInfoEntity.sid)
        }

        @JvmStatic
        fun isRealName(userInfoEntity: UserInfoEntity?): Boolean {
            return userInfoEntity?.hasRealName ?: false
        }

    }

    private val mDataStoreManger: DataStoreManger = DataStoreManger.get()


    suspend fun putEntity(userInfoEntity: UserInfoEntity?) {
        if (userInfoEntity == null) {
            return
        }
        putAddress(userInfoEntity.address)
        putPayPassword(userInfoEntity.hasPayPassword)
        putRealName(userInfoEntity.hasRealName)
        putHeader(userInfoEntity.header)
        putId(userInfoEntity.id)
        putInviteCode(userInfoEntity.inviteCode)
        putMobile(userInfoEntity.mobile)
        putNickName(userInfoEntity.nickName)
        putPrivateKey(userInfoEntity.privateKey)
        putSid(userInfoEntity.sid)
    }

    suspend fun getEntity(): UserInfoEntity? {
        return if (isLogin()) {
            val entity = UserInfoEntity(getId())
            entity.address = getAddress()
            entity.hasPayPassword = isPayPassWord()
            entity.hasRealName = isRealName()
            entity.header = getHeader()
            entity.inviteCode = getInviteCode()
            entity.mobile = getMobile()
            entity.nickName = getNickName()
            entity.privateKey = getPrivateKey()
            entity.sid = getSid()
            entity
        } else {
            null
        }

    }

    private suspend fun putAddress(address: String?) {
        mDataStoreManger.putObject(KEY_ADDRESS, address ?: "")
    }

    private suspend fun getAddress() = mDataStoreManger.getString(KEY_ADDRESS)

    private suspend fun putPayPassword(hasPayPassword: Boolean?) {
        mDataStoreManger.putObject(KEY_HAS_PAY_PASSWORD, hasPayPassword ?: false)
    }

    private suspend fun isPayPassWord() = mDataStoreManger.getBoolean(KEY_HAS_PAY_PASSWORD)

    suspend fun putRealName(hasRealName: Boolean?) {
        mDataStoreManger.putObject(KEY_HAS_REAL_NAME, hasRealName ?: "")
    }

    private suspend fun isRealName() = mDataStoreManger.getBoolean(KEY_HAS_REAL_NAME)

    private suspend fun putHeader(header: String?) {
        mDataStoreManger.putObject(KEY_HEADER, header ?: "")
    }

    private suspend fun getHeader() = mDataStoreManger.getString(KEY_HEADER)

    private suspend fun putId(id: String?) {
        mDataStoreManger.putObject(KEY_ID, id ?: "")
    }

    suspend fun getId() = mDataStoreManger.getString(KEY_ID)

    private suspend fun putInviteCode(inviteCode: String?) {
        mDataStoreManger.putObject(KEY_INVITE_CODE, inviteCode ?: "")
    }

    private suspend fun getInviteCode() = mDataStoreManger.getString(KEY_INVITE_CODE)

    private suspend fun putMobile(mobile: String?) {
        mDataStoreManger.putObject(KEY_MOBILE, mobile ?: "")
    }

    suspend fun getMobile(): String = mDataStoreManger.getString(KEY_MOBILE)
    private suspend fun putNickName(nickName: String?) {
        mDataStoreManger.putObject(KEY_NICK_NAME, nickName ?: "")
    }

    private suspend fun getNickName() = mDataStoreManger.getString(KEY_NICK_NAME)
    private suspend fun putPrivateKey(privateKey: String?) {
        mDataStoreManger.putObject(KEY_PRIVATE_KEY, privateKey ?: "")
    }

    private suspend fun getPrivateKey() = mDataStoreManger.getString(KEY_PRIVATE_KEY)
    private suspend fun putSid(sid: String?) {
        mDataStoreManger.putObject(KEY_SID, sid ?: "")
    }

     suspend fun getSid() = mDataStoreManger.getString(KEY_SID)
    suspend fun isLogin() = DataCompat.notEmpty(getSid())

    suspend fun clear() {
        mDataStoreManger.clear()
    }
}