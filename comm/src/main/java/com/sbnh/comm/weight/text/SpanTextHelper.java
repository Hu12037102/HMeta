package com.sbnh.comm.weight.text;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sbnh.comm.compat.DataCompat;
import com.sbnh.comm.compat.PhoneCompat;
import com.sbnh.comm.compat.UICompat;
import com.sbnh.comm.utils.LogUtils;

import org.jetbrains.annotations.NotNull;


/**
 * 作者: 胡庆岭
 * 创建时间: 2021/6/26 16:30
 * 更新时间: 2021/6/26 16:30
 * 描述: 文本文字帮助类
 */
public final class SpanTextHelper {
    private final SpannableStringBuilder mSpanBuilder;
    private CharSequence mAppendText;
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private int mStartIndex;
    private int mEndIndex;
    private static final String IMAGE_TAG = "<image>";

    private SpanTextHelper() {
        mSpanBuilder = new SpannableStringBuilder();
    }

    public static SpanTextHelper with() {
        return new SpanTextHelper();
    }

    public SpanTextHelper append(@NonNull CharSequence text) {
        mAppendText = text;
        appendText(mAppendText);
        return this;
    }

    public SpanTextHelper appendLine() {
        mAppendText = SpanTextHelper.LINE_SEPARATOR;
        LogUtils.w("appendLine--", mAppendText + "--");
        appendText(mAppendText);
        return this;
    }

    public SpanTextHelper appendBlank() {
        mAppendText = " ";
        appendText(mAppendText);
        return this;
    }

    private void appendText(CharSequence text) {
        /*mStartIndex = DataUtils.getLength(mSpanBuilder);
        mSpanBuilder.append(text);
        mEndIndex = DataUtils.getLength(mSpanBuilder);*/
        mStartIndex = DataCompat.getTextLength(mSpanBuilder);
        mSpanBuilder.append(text);
        mEndIndex = DataCompat.getTextLength(mSpanBuilder);
    }

    public SpanTextHelper appendDrawable(@DrawableRes int resDrawable) {/*
        Drawable drawable = UIUtils.getDrawable(resDrawable);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());*/
        CenterImageSpan imageSpan = new CenterImageSpan(DataCompat.getContext(), resDrawable, CenterImageSpan.ALIGN_FONTCENTER);
        // ImageSpan imageSpan = new ImageSpan(drawable, ImageSpan.ALIGN_CENTER);
        appendText(SpanTextHelper.IMAGE_TAG);
        mSpanBuilder.setSpan(imageSpan, mStartIndex, mEndIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;

    }


    public SpanTextHelper setColor(@ColorInt int textColor) {
        ForegroundColorSpan fcs = new ForegroundColorSpan(textColor);
        mSpanBuilder.setSpan(fcs, mStartIndex, mEndIndex, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    public SpanTextHelper setSize(int textSize, boolean isDip) {
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(textSize, isDip);
        mSpanBuilder.setSpan(ass, mStartIndex, mEndIndex, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    public SpanTextHelper setClick(@ColorInt int clickColor, @Nullable ClickText.OnClickTextListener listener) {
        ClickText clickText = new ClickText(clickColor, listener);
        mSpanBuilder.setSpan(clickText, mStartIndex, mEndIndex, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    public void crete(TextView textView) {
        UICompat.setText(textView, mSpanBuilder);
        if (textView != null) {
            textView.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

    public SpannableStringBuilder getSpan() {
        return mSpanBuilder;
    }

    public static class ClickText extends ClickableSpan {
        private final OnClickTextListener onClickTextListener;
        private final @ColorInt
        int colorInt;

        public ClickText(@ColorInt int colorInt, @Nullable OnClickTextListener onClickTextListener) {
            this.onClickTextListener = onClickTextListener;
            this.colorInt = colorInt;
        }

        @Override
        public void onClick(@NonNull View widget) {
            if (onClickTextListener != null) {
                onClickTextListener.onClickText(widget);
            }
        }

        @Override
        public void updateDrawState(@NonNull TextPaint ds) {
            super.updateDrawState(ds);
            ds.setColor(colorInt);
            ds.setUnderlineText(false);
        }

        public interface OnClickTextListener {
            void onClickText(@NonNull View view);
        }
    }

    public static class CenterImageSpan extends ImageSpan {
        public static final int ALIGN_FONTCENTER = -2;
        private int mDrawableWidth, mDrawableHeight;

        public CenterImageSpan(Context context, int resourceId) {
            super(context, resourceId);
        }

        public CenterImageSpan(Context context, int resourceId, int verticalAlignment) {
            super(context, resourceId, verticalAlignment);
        }

        public CenterImageSpan(Context context, int resourceId, int verticalAlignment, int drawableWidth, int drawableHeight) {
            super(context, resourceId, verticalAlignment);
            this.mDrawableWidth = drawableWidth;
            this.mDrawableHeight = drawableHeight;
        }

        public CenterImageSpan(@NonNull Drawable drawable, int verticalAlignment, int drawableWidth, int drawableHeight) {
            super(drawable, verticalAlignment);
            this.mDrawableWidth = drawableWidth;
            this.mDrawableHeight = drawableHeight;
        }


        @Override
        public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom,
                         Paint paint) {

            //draw 方法是重写的ImageSpan父类 DynamicDrawableSpan中的方法，在DynamicDrawableSpan类中，虽有getCachedDrawable()，
            // 但是私有的，不能被调用，所以调用ImageSpan中的getrawable()方法，该方法中 会根据传入的drawable ID ，获取该id对应的
            // drawable的流对象，并最终获取drawable对象
            Drawable drawable = getDrawable(); //调用imageSpan中的方法获取drawable对象
            canvas.save();

            //获取画笔的文字绘制时的具体测量数据
            Paint.FontMetricsInt fm = paint.getFontMetricsInt();

            //系统原有方法，默认是Bottom模式)
            int transY = bottom - drawable.getBounds().bottom;
            if (mVerticalAlignment == ALIGN_BASELINE) {
                transY -= fm.descent;
            } else if (mVerticalAlignment == ALIGN_FONTCENTER) {    //此处加入判断， 如果是自定义的居中对齐
                //与文字的中间线对齐（这种方式不论是否设置行间距都能保障文字的中间线和图片的中间线是对齐的）
                // y+ascent得到文字内容的顶部坐标，y+descent得到文字的底部坐标，（顶部坐标+底部坐标）/2=文字内容中间线坐标
                transY = ((y + fm.descent) + (y + fm.ascent)) / 2 - drawable.getBounds().bottom / 2;
            }

            canvas.translate(x, transY);
            drawable.draw(canvas);
            canvas.restore();
        }

        @Override
        public int getSize(@NotNull Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
            Drawable d = getDrawable();
            Rect rect = d.getBounds();
            if (fm != null) {
                Paint.FontMetricsInt fmPaint = paint.getFontMetricsInt();
                int fontHeight = fmPaint.bottom - fmPaint.top;
                int drHeight = rect.bottom - rect.top;

                int top = drHeight / 2 - fontHeight / 4;
                int bottom = drHeight / 2 + fontHeight / 4;

                fm.ascent = -bottom;
                fm.top = -bottom;
                fm.bottom = top;
                fm.descent = top;
                //  rect.offset(ScreenUtils.dp2px(UIUtils.getBaseContext(),2),0);
            }
            return rect.right + PhoneCompat.dp2px(DataCompat.getContext(), 2);
        }

        @Override
        public Drawable getDrawable() {
            Drawable drawable = super.getDrawable();
            if (mDrawableHeight > 0 && mDrawableWidth > 0) {
                drawable.setBounds(0, 0, mDrawableWidth, mDrawableHeight);
            }
            return drawable;
        }


    }
}
