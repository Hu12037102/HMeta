package com.sbnh.comm.weight.view

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.Px
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.sbnh.comm.R
import com.sbnh.comm.compat.DataCompat
import com.sbnh.comm.compat.PhoneCompat
import com.sbnh.comm.compat.UICompat
import com.sbnh.comm.databinding.BaseParentEmptyViewBinding

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/10 16:57
 * 更新时间: 2022/6/10 16:57
 * 描述:
 */
class EmptyLayout : ConstraintLayout {
    private var mViewBinding: BaseParentEmptyViewBinding? = null

    @Px
    private var mTextSize: Int = DataCompat.dimen2Int(context, R.dimen.default_text_size)

    @DrawableRes
    private var mResSrc: Int = R.mipmap.icon_comm_empty

    @ColorInt
    private var mTextColor: Int = ContextCompat.getColor(context, R.color.colorFF9A9A9C)
    private var mText: String? = DataCompat.getResString(R.string.this_is_null)

    constructor (
        context: Context
    ) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs, 0) {

        val typeArray: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.EmptyLayout)
        mResSrc =
            typeArray.getResourceId(R.styleable.EmptyLayout_srcCompat, R.mipmap.icon_comm_empty)
        mTextSize = typeArray.getDimensionPixelSize(
            R.styleable.EmptyLayout_android_textSize, mTextSize
        )
        mTextColor = typeArray.getColor(R.styleable.EmptyLayout_android_textColor, mTextColor)
        mText = typeArray.getString(R.styleable.EmptyLayout_android_text)
        typeArray.recycle()

    }

    init {

        mViewBinding = BaseParentEmptyViewBinding.inflate(LayoutInflater.from(context), this, true)
        // addView(mViewBinding?.root)
        UICompat.setImageRes(mViewBinding?.aivEmptyContent, mResSrc)
        mViewBinding?.atvEmptyContent?.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize.toFloat())
        UICompat.setTextColor(mViewBinding?.atvEmptyContent, mTextColor)
        UICompat.setText(mViewBinding?.atvEmptyContent, mText)
    }

    fun setText(text: CharSequence) {
        UICompat.setText(mViewBinding?.atvEmptyContent, text)
    }

    fun setTextColor(@ColorInt colorInt: Int) {
        UICompat.setTextColor(mViewBinding?.atvEmptyContent, colorInt)
    }

    fun show() {
        visibility = VISIBLE
    }

    fun hide() {
        visibility = GONE
    }
}