package com.sbnh.comm.compat

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.sbnh.comm.Contract
import com.sbnh.comm.utils.LogUtils

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/12 20:21
 * 更新时间: 2022/6/12 20:21
 * 描述:
 */
object MetaViewCompat {
    @JvmStatic
    fun setViewSize(
        view: View?,
        width: Int = ViewGroup.LayoutParams.MATCH_PARENT,
        height: Int = ViewGroup.LayoutParams.MATCH_PARENT
    ) {
        if (view == null) {
            return
        }
        var params = view.layoutParams
        if (DataCompat.isNull(params)) {
            params = ViewGroup.LayoutParams(width, height)
        } else {
            params.width = width
            params.height = height
        }
        view.layoutParams = params
    }

    @JvmStatic
    @ColorInt
    fun getColor(@ColorRes colorRes: Int): Int =
        ContextCompat.getColor(DataCompat.getContext(), colorRes)

    @JvmStatic
    fun getDrawable(@DrawableRes drawableRes: Int): Drawable? =
        ContextCompat.getDrawable(DataCompat.getContext(), drawableRes)

    @JvmStatic
    fun setClickButton(view: View?, dpValue: Float) {
        if (DataCompat.notNull(view)) {
            ViewCompat.setBackground(view!!, GradientDrawableCompat.createClickDrawable(dpValue))
        }
    }

    @JvmStatic
    fun setClickViewEnable(view: View?, isClick: Boolean) {
        if (DataCompat.notNull(view)) {
            val drawable =
                if (isClick) GradientDrawableCompat.createClickDrawable(Contract.DP.VALUE_8F)
                else GradientDrawableCompat.createNoClickDrawable(Contract.DP.VALUE_8F)
            view?.isEnabled = isClick
            ViewCompat.setBackground(view!!, drawable)

        }
    }

    @JvmStatic
    fun textViewTextIsEmpty(textView: TextView?): Boolean {
        return DataCompat.isEmpty(textView?.text)
    }

    @JvmStatic
    fun getTextViewText(textView: TextView?): CharSequence {
        return DataCompat.checkNotNull(textView?.text)
    }

    @JvmStatic
    fun getTextViewText(textView: TextView?, isTrim: Boolean): CharSequence {
        val text: CharSequence? =
            if (isTrim) textView?.text?.trim()?.toString()?.replace(" ", "") else textView?.text
        return DataCompat.checkNotNull(text)
    }


    @JvmStatic
    fun showSoftKeyBoard(view: View?) {
        view?.let {
            if (it is EditText) {
                it.requestFocus()
                // it.isFocusableInTouchMode= true
                // it.isFocusable = true
            }


            ViewCompat.getWindowInsetsController(it)?.show(WindowInsetsCompat.Type.ime())
        }

    }

    @JvmStatic
    fun hideSoftKeyBoard(view: View?) {
        view?.let {
            ViewCompat.getWindowInsetsController(it)?.hide(WindowInsetsCompat.Type.ime())
        }
    }

    @JvmStatic
    fun finishActivity(activity: Activity?) {
        if (activity == null) {
            return
        }
        activity.finish()
    }

    @JvmStatic
    fun finishActivitySetResult(activity: Activity?, intent: Intent? = null) {
        if (activity == null) {
            return
        }
        activity.setResult(Activity.RESULT_OK, intent)
        finishActivity(activity)
    }

    @JvmStatic
    fun setResultOK(activity: Activity?, data: Intent? = null) {
        if (activity == null) {
            return
        }
        activity.setResult(Activity.RESULT_OK, data)
    }

    @JvmStatic
    fun getTextViewLength(textView: TextView?): Int = getTextViewLength(textView, false)

    @JvmStatic
    fun getTextViewLength(textView: TextView?, isTrim: Boolean): Int {
        return if (textView == null) {
            0
        } else {
            DataCompat.getTextLength(getTextViewText(textView), isTrim)
        }
    }

    @JvmStatic
    fun selectorEditTextEnd(textView: EditText?) {
        textView?.apply {
            setSelection(getTextViewLength(textView))
        }
    }

    @JvmStatic
    fun setStatusBarMargin(view: View?, activity: Activity?) {
        if (view == null || activity == null) {
            return
        }
        var layoutMargin = view.layoutParams
        if (layoutMargin !is ViewGroup.MarginLayoutParams) {
            layoutMargin = ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
        layoutMargin.topMargin = PhoneCompat.getStatusBarHeight(activity)
        LogUtils.w("setStatusBarMargin--", "${PhoneCompat.getStatusBarHeight(activity)}")
        view.layoutParams = layoutMargin
    }
}