package com.sbnh.comm.compat

import android.content.Context
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DimenRes
import androidx.annotation.Nullable
import androidx.annotation.StringRes
import androidx.core.content.pm.PackageInfoCompat
import com.sbnh.comm.app.BaseApplication
import java.util.*

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/10 12:05
 * 更新时间: 2022/6/10 12:05
 * 描述:
 */


object DataCompat {
    @JvmStatic
    fun isEmpty(text: CharSequence?): Boolean {
        return TextUtils.isEmpty(text)
    }

    @JvmStatic
    fun checkNotNull(text: CharSequence?, defaultText: CharSequence = ""): CharSequence {
        return if (isEmpty(text)) defaultText else text!!
    }


    @JvmStatic
    fun dimen2Int(context: Context, @DimenRes dimenRes: Int): Int =
        context.resources.getDimensionPixelSize(dimenRes)

    @JvmStatic
    fun <T> notNull(t: T?): Boolean = t != null

    @JvmStatic
    @Nullable
    fun <T> isNull(t: T?): Boolean = t == null

    @JvmStatic
    fun getResString(@StringRes resString: Int): String {
        return getContext().getString(resString)
    }

    @JvmStatic
    fun getResString(@StringRes resString: Int, vararg formatArgs: Any): String {
        return getContext().getString(resString, *formatArgs)
    }

    @JvmStatic
    fun getContext(): Context {
        return BaseApplication.getContext()
    }

    @JvmStatic
    fun notEmpty(text: CharSequence?): Boolean {
        return !isEmpty(text)
    }

    @JvmStatic
    fun checkContext(context: Context?): Context {
        return context ?: getContext()
    }

    @JvmStatic
    fun getTextLength(text: CharSequence?): Int {
        return getTextLength(text, false)
    }

    @JvmStatic
    fun getTextLength(text: CharSequence?, isTrim: Boolean): Int = toString(text, isTrim).length

    @JvmStatic
    fun toString(any: Any?): String = toString(any, false)

    @JvmStatic
    fun toString(any: Any?, isTrim: Boolean): String =
        if (isTrim) any?.toString()?.trim()?.replace(" ", "") ?: "" else any?.toString() ?: ""

    @JvmStatic
    fun getVersionCode(): Long {
        val packInfo = getPackInfo()
        return PackageInfoCompat.getLongVersionCode(packInfo)
    }

    @JvmStatic
    fun getVersionName(): String {
        return getPackInfo().versionName
    }

    @JvmStatic
    private fun getPackInfo() =
        getContext().packageManager.getPackageInfo(getContext().packageName, 0)

    @JvmStatic
    fun getOnlyId(): String {
        var id = ""
        var uuid = toString(UUID.randomUUID().toString())
        uuid = uuid.replace("-", "")
        if (uuid.length > 8) {
            uuid = uuid.substring(uuid.length - 8)
        }
        id += uuid
        var timestamp = "${System.currentTimeMillis()}"
        if (timestamp.length > 8) {
            timestamp = timestamp.substring(timestamp.length - 8)
        }
        id += timestamp
        id += (Math.random() * 10000).toInt()
        return id
    }



}