package com.sbnh.comm.compat

import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.TypedValue
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import com.sbnh.comm.utils.LogUtils
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
    fun setBankCardNumberEditText(editText: EditText?) {
        /*  val textLength = MetaViewCompat.getTextViewLength(editText)
          if (textLength > 0) {
              val sb = StringBuilder()
              val text = MetaViewCompat.getTextViewText(editText,true)
              sb.append(text)
              if ((textLength % 5 == 0) && !TextUtils.equals(
                      text[textLength - 1].toString(),
                      " "
                  )
              ) {
                  sb.insert(textLength - 1, " ")
                  setText(editText, sb)
                  MetaViewCompat.selectorEditTextEnd(editText)
              }
          }*/
        val textLength = MetaViewCompat.getTextViewLength(editText, true)
        if (textLength > 0) {
            val text = MetaViewCompat.getTextViewText(editText)
            val sb = StringBuilder()
            for (childText in text) {
                sb.append(childText)
                val index = text.indexOf(childText)
                if (index % 5 == 0) {
                    sb.append(" ")
                }
            }
            if (!TextUtils.equals(sb, text)) {
                setText(editText, sb)
                MetaViewCompat.selectorEditTextEnd(editText)
            }

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
    fun <T> notifyAdapterDateChanged(
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
        notifyDataEmptyView(emptyView,parentData)
    }
}


