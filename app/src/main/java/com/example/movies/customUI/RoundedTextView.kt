package com.example.movies.customUI

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import androidx.annotation.ColorRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.example.movies.R
import kotlin.math.min

class RoundedTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private val backgroundDrawable = GradientDrawable()

    init {
        backgroundDrawable.shape = GradientDrawable.OVAL
        backgroundDrawable.setColor(
            ContextCompat.getColor(context, R.color.white)
        )
        background = backgroundDrawable
    }

    fun setRoundedBackgroundColor(@ColorRes colorResId: Int) {
        backgroundDrawable.setColor(ContextCompat.getColor(context, colorResId))
    }

    override fun onMeasure(widthSpec: Int, heightSpec: Int) {
        super.onMeasure(widthSpec, heightSpec)
        val size = min(measuredWidth, measuredHeight)
        setMeasuredDimension(size, size)
    }
}