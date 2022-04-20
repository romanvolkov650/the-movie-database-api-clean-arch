package com.romanvolkov.themovie.common

import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.PorterDuff
import android.os.Build
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.romanvolkov.themovie.R
import com.romanvolkov.themovie.common.ToastConstants.TYPE_ERROR
import com.romanvolkov.themovie.common.ToastConstants.TYPE_FAIL
import splitties.init.appCtx

object Toast {
    var toast = Toast(appCtx)

    // выводим тостер в зависимости от type
    fun show(message: String, type: Int): Toast {

        // закрываем предыдущий
        toast.cancel()
        showToast(message = message, type = type)
        return toast
    }

    // выводим тостер в зависимости от type
    fun show(messageRes: Int, type: Int): Toast {

        // закрываем предыдущий
        toast.cancel()
        showToast(message = appCtx.getString(messageRes), type = type)
        return toast
    }

    // показать тостер
    private fun showToast(message: String, type: Int) {
        toast = Toast(appCtx).also {
            it.duration = Toast.LENGTH_SHORT
            it.setGravity(Gravity.TOP or Gravity.FILL_HORIZONTAL, 0, 0)
            val view = View.inflate(appCtx, R.layout.toast_layout, null)
            val textView = view.findViewById<TextView>(R.id.screen_start_toast_text)
            textView.apply {
                textView.text = message
                setBGColor(getToasterColor(type))
            }
            it.view = view
            it.show()
        }
    }

    // получает цвет тостера по типу
    private fun getToasterColor(type: Int): Int {
        return when (type) {
            TYPE_ERROR -> R.color.orange
            TYPE_FAIL -> R.color.grey
            else -> R.color.green
        }
    }

    // установить цвет фона тостера
    private fun TextView.setBGColor(colorRes: Int) {
        val color = ContextCompat.getColor(context, colorRes)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            this.background.colorFilter = BlendModeColorFilter(
                color, BlendMode.SRC
            )
        } else {
            this.background.setColorFilter(color, PorterDuff.Mode.SRC)
        }
    }
}