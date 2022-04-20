package com.romanvolkov.themovie.common.views

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.isVisible
import by.kirich1409.viewbindingdelegate.viewBinding
import com.romanvolkov.themovie.R
import com.romanvolkov.themovie.databinding.ViewPlaceholderBinding
import timber.log.Timber

class PlaceholderView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding by viewBinding(ViewPlaceholderBinding::bind)

    init {
        inflate(context, R.layout.view_placeholder, this)
        setAttrs(context, attrs)
    }

    // установка параметров
    private fun setAttrs(context: Context, attrs: AttributeSet?) {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.PlaceholderView,
            0, 0
        ).apply {
            try {
                isShowButton()
                setTitle()
                setTextButton()
                setImage()
            } finally {
                recycle()
            }
        }
    }

    // установка видимость кнопки через атрибуты
    private fun TypedArray.isShowButton() {
        val isShowButton = getBoolean(R.styleable.PlaceholderView_isShowButton, false)
        binding.buttonPlaceholder.isVisible = isShowButton
    }

    // установка видимость кнопки
    fun isShowButton(isShowButton: Boolean) {
        binding.buttonPlaceholder.isVisible = isShowButton
    }

    // установка текста заглушки через атрибуты
    private fun TypedArray.setTitle() {
        val title = getString(R.styleable.PlaceholderView_textTitlePlaceholder)
        binding.textPlaceholder.text = title
    }

    // установка текста у заглушки
    fun setTitle(title: Int) {
        binding.textPlaceholder.setText(title)
    }

    // установить цвет текст
    fun setTextColor(color: Int) {
        binding.textPlaceholder.setTextColor(color)
    }

    // установить размер шрифта
    fun setTextSize(size: Float) {
        binding.textPlaceholder.textSize = size
    }

    // установить расстояние между строками
    fun setLineHeight(multiplyNum: Float, height: Float) {
        binding.textPlaceholder.setLineSpacing(height, multiplyNum)
    }

    // установка текста у кнопки через атрибуты
    private fun TypedArray.setTextButton() {
        val title = getResourceId(
            R.styleable.PlaceholderView_textButtonPlaceholder,
            R.string.update
        )
        binding.buttonPlaceholder.setText(title)
    }

    // установка текста у кнопки
    fun setTextButton(title: Int) {
        binding.buttonPlaceholder.setText(title)
    }

    // установка действия на нажатие
    fun setClickButton(action: () -> Unit) {
        binding.buttonPlaceholder.clickResetInternetConnection(action)
    }

    fun globeClick(action: () -> Unit) {
        binding.imagePlaceholder.setOnClickListener {
            action.invoke()
        }
    }

    // установка иконки через атрибуты
    private fun TypedArray.setImage() {
        val imageRes = getResourceId(
            R.styleable.PlaceholderView_imagePlaceholder,
            R.drawable.ic_internet
        )
        binding.imagePlaceholder.setImageResource(imageRes)
    }

    // установка иконки
    fun setImage(imageRes: Int) {
        binding.imagePlaceholder.setImageResource(imageRes)
    }
}