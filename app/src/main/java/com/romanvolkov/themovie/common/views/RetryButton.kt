package com.romanvolkov.themovie.common.views

import android.content.Context
import android.content.res.TypedArray
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import by.kirich1409.viewbindingdelegate.viewBinding
import com.romanvolkov.themovie.R
import com.romanvolkov.themovie.databinding.ViewButtonUpdateBinding

class RetryButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: ViewButtonUpdateBinding by viewBinding(ViewButtonUpdateBinding::bind)
    private val rotation: Animation

    init {
        inflate(context, R.layout.view_button_update, this)
        rotation = AnimationUtils.loadAnimation(context, R.anim.rotate_infinite)
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

                // установка текста
                setText()

                // установка цвета границ
                setColorBorder()

                // установка цвета текста
                setColorText()
            } finally {
                recycle()
            }
        }
    }

    // установка текста
    private fun TypedArray.setText() {
        val textRes = getResourceId(
            R.styleable.RetryButton_textResButton, R.string.update
        )
        binding.tvBtnInternetConnectionPersonality.setText(textRes)
    }

    // установка цвета бордера
    private fun TypedArray.setColorBorder() {
        val colorBorder = getResourceId(
            R.styleable.RetryButton_borderColorResButton,
            R.drawable.bg_grey_border_5dp
        )
        binding.btnUpdateContainer.setBackgroundResource(colorBorder)
    }

    // установка цвета текста
    private fun TypedArray.setColorText() {
        val colorText = getResourceId(
            R.styleable.RetryButton_textColorResButton,
            R.color.white
        )
        binding.tvBtnInternetConnectionPersonality.setTextColor(
            ContextCompat.getColor(
                context,
                colorText
            )
        )
    }

    // установка клика по кнопке и появление прелоудера
    fun clickResetInternetConnection(action: (() -> Unit)? = null) {
        binding.apply {
            btnUpdateContainer.setOnClickListener {
                tvBtnInternetConnectionPersonality.isGone = true
                ivProgressBarNoConnection.isVisible = true
                btnUpdateContainer.isClickable = false
                btnUpdateContainer.isFocusable = false
                ivProgressBarNoConnection.startAnimation(rotation)
                Handler(Looper.getMainLooper()).postDelayed({
                    action?.invoke()
                    btnUpdateContainer.isClickable = true
                    btnUpdateContainer.isFocusable = true
                    ivProgressBarNoConnection.clearAnimation()
                    ivProgressBarNoConnection.isGone = true
                    tvBtnInternetConnectionPersonality.isVisible = true
                }, 2000)
            }
        }
    }

    // установить текст
    fun setText(textRes: Int) {
        binding.tvBtnInternetConnectionPersonality.setText(textRes)
    }

    // установить цвет текста
    fun setColorText(colorId: Int) {
        binding.tvBtnInternetConnectionPersonality.setTextColor(
            ContextCompat.getColor(
                context,
                colorId
            )
        )
    }

    // установить фон кнопки
    fun setBackground(resourceId: Int) {
        binding.btnUpdateContainer.setBackgroundResource(resourceId)
    }
}