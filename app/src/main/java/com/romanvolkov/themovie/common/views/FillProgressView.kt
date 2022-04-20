package com.romanvolkov.themovie.common.views

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import by.kirich1409.viewbindingdelegate.viewBinding
import com.romanvolkov.themovie.R
import com.romanvolkov.themovie.databinding.ViewFillProgressBinding

class FillProgressView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding by viewBinding(ViewFillProgressBinding::bind)

    init {
        inflate(context, R.layout.view_fill_progress, this)
        setAttrs(context, attrs)
    }

    // установка параметров
    private fun setAttrs(context: Context, attrs: AttributeSet?) {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.RetryButton,
            0, 0
        ).apply {
            try {
                setPercent()
            } finally {
                recycle()
            }
        }
    }

    // установка значения процентов
    private fun TypedArray.setPercent() {
        val percentValue = getInt(R.styleable.FillProgressView_percentVal, 0)
        setPercent(percentValue)
    }

    fun setPercent(value: Int) {
        binding.progress.progress = value
        val colors: Pair<Int, Int> = when (value) {
            in 0..1 -> {
                Pair(R.color.grey_na, R.color.grey_na)
            }
            in 1..39 -> {
                Pair(R.color.purple_dark, R.color.purple)
            }
            in 40..59 -> {
                Pair(R.color.yellow_dark, R.color.yellow)
            }
            else -> {
                Pair(R.color.green_v2_dark, R.color.green_v2)
            }
        }
        binding.progress.backgroundTintList = ContextCompat.getColorStateList(context, colors.first)
        binding.progress.progressTintList = ContextCompat.getColorStateList(context, colors.second)
        if (value == 0) {
            binding.percent.isGone = true
            binding.tvPercent.text = context.getString(R.string.n_a)
        } else {
            binding.percent.isVisible = true
            binding.tvPercent.text = "$value"
        }
    }
}