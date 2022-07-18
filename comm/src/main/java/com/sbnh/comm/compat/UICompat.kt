package com.sbnh.comm.compat

import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import com.huxiaobai.adapter.BaseRecyclerAdapter
import com.sbnh.comm.weight.view.EmptyLayout

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/10 15:09
 * 更新时间: 2022/6/10 15:09
 * 描述:
 */
object UICompat {
    @JvmStatic
    fun setText(textView: TextView?, text: CharSequence? = "") {
        textView?.text = text
    }

    @JvmStatic
    fun setText(textView: TextView?, @StringRes res: Int) {
        textView?.text = DataCompat.getResString(res)
    }

    @JvmStatic
    fun setText(textView: TextView?, @StringRes res: Int, vararg formatAny: Any) {
        textView?.text = DataCompat.getResString(res, *formatAny)
    }

    @JvmStatic
    fun setTextSize(textView: TextView?, sizeSp: Float) {
        textView?.setTextSize(TypedValue.COMPLEX_UNIT_DIP, sizeSp)
    }

    @JvmStatic
    fun setImageRes(imageView: ImageView?, @DrawableRes resDrawable: Int?) {
        imageView?.setImageResource(resDrawable ?: 0)
    }

    @JvmStatic
    fun setImageDrawable(imageView: ImageView?, drawable: Drawable? = null) {
        imageView?.setImageDrawable(drawable)
    }

    @JvmStatic
    fun setTextColor(textView: TextView?, @ColorInt textColor: Int) {
        textView?.setTextColor(textColor)
    }

    @JvmStatic
    fun setTextColorRes(textView: TextView?, @ColorRes textColor: Int) {
        textView?.setTextColor(MetaViewCompat.getColor(textColor))
    }

    @JvmStatic
    fun setPhoneEditText(editText: EditText?) {
        val textLength = MetaViewCompat.getTextViewLength(editText)
        if (textLength > 0) {
            val sb = StringBuilder()
            val text = MetaViewCompat.getTextViewText(editText)
            sb.append(text)
            if ((textLength == 4 || textLength == 9) && !TextUtils.equals(
                    text[textLength - 1].toString(),
                    " "
                )
            ) {
                sb.insert(textLength - 1, " ")
                setText(editText, sb)
                MetaViewCompat.selectorEditTextEnd(editText)
            }
        }
    }

    @JvmStatic
    fun setPhoneEditText(
        editText: EditText,
        text: CharSequence?,
        start: Int,
        before: Int
    ) {
        if (text == null || text.isEmpty()) return
        val sb = StringBuilder()
        for (i in text.indices) {
            if (i != 3 && i != 8 && text[i] == ' ') {
                continue
            } else {
                sb.append(text[i])
                if ((sb.length == 4 || sb.length == 9) && sb[sb.length - 1] != ' ') {
                    sb.insert(sb.length - 1, ' ')
                }
            }
        }
        if (sb.toString() != text.toString()) {
            var index = start + 1
            if (sb[start] == ' ') {
                if (before == 0) {
                    index++
                } else {
                    index--
                }
            } else {
                if (before == 1) {
                    index--
                }
            }
            setText(editText, sb.toString())
            editText.setSelection(index)
        }

    }

    @JvmStatic
    fun setBankCardNumberEditText(
        editText: EditText,
        text: CharSequence?,
        start: Int,
        before: Int
    ) {
        if (text == null || text.isEmpty()) return
        val sb = StringBuilder()
        for (i in text.indices) {
            if (i != 4 && i != 9 && i != 14 && i != 19 && text[i] == ' ') {
                continue
            } else {
                sb.append(text[i])
                if ((sb.length == 5 || sb.length == 10 || sb.length == 15 || sb.length == 20) && sb[sb.length - 1] != ' ') {
                    sb.insert(sb.length - 1, ' ')
                }
            }
        }
        if (sb.toString() != text.toString()) {
            var index = start + 1
            if (sb[start] == ' ') {
                if (before == 0) {
                    index++
                } else {
                    index--
                }
            } else {
                if (before == 1) {
                    index--
                }
            }
            setText(editText, sb.toString())
            editText.setSelection(index)
        }


    }

    @JvmStatic
    fun notifyDataEmptyView(emptyView: EmptyLayout?, data: List<*>?) {
        if (emptyView == null || data == null) {
            return
        }
        if (CollectionCompat.isEmptyList(data)) {
            emptyView.show()
        } else {
            emptyView.hide()
        }
    }

    @JvmStatic
    fun <T> notifyAdapterAddDateChanged(
        emptyView: EmptyLayout?,
        adapter: RecyclerView.Adapter<*>?,
        isRefresh: Boolean,
        parentData: ArrayList<T>,
        addData: List<T>?
    ) {
        if (isRefresh) {
            parentData.clear()
        }
        if (CollectionCompat.notEmptyList(addData)) {
            parentData.addAll(addData!!)
        }
        adapter?.notifyDataSetChanged()
        notifyDataEmptyView(emptyView, parentData)
    }

    @JvmStatic
    fun <T> notifyAdapterUpdateDateChanged(
        emptyView: EmptyLayout?,
        adapter: RecyclerView.Adapter<*>?,
        parentData: ArrayList<T>,
    ) {
        adapter?.notifyDataSetChanged()
        notifyDataEmptyView(emptyView, parentData)
    }

    @JvmStatic
    fun showRecyclerViewDataEmptyView(parentView: View?, emptyView: EmptyLayout?) {
        if (emptyView == null) {
            return
        }

        if (parentView is ViewGroup) {
            if (parentView is RecyclerView) {
                val adapter = parentView.adapter
                if (adapter is BaseRecyclerAdapter<*> && adapter.itemCount > 0) {
                    emptyView.hide()
                    return
                } else {
                    emptyView.show()
                }
            } else {
                for (i in 0 until parentView.childCount) {
                    val childView = parentView.getChildAt(i)
                    showRecyclerViewDataEmptyView(childView, emptyView)
                }
            }
        } else {
            emptyView.show()
        }

    }

}


