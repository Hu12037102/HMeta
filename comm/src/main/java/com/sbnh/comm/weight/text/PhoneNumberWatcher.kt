package com.sbnh.comm.weight.text

import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.widget.EditText
import com.sbnh.comm.compat.MetaViewCompat
import com.sbnh.comm.compat.UICompat

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/28 10:36
 * 更新时间: 2022/6/28 10:36
 * 描述:
 */
open class PhoneNumberWatcher(private val editText: EditText) : TextWatcher {


    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        val textLength = MetaViewCompat.getTextViewLength(editText)
        if (textLength > 0) {
            val sb = StringBuilder()
            val text = MetaViewCompat.getTextViewText(editText)
            sb.append(text)
            if ((textLength== 4 || textLength == 9)&& !TextUtils.equals(text[textLength - 1].toString(), " ")){
                sb.insert(textLength - 1," ")
                UICompat.setText(editText, sb)
                MetaViewCompat.selectorEditTextEnd(editText)
            }
        }

    }

    override fun afterTextChanged(s: Editable?) {

    }
}