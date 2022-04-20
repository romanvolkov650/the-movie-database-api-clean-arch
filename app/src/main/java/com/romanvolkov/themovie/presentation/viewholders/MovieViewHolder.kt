package com.romanvolkov.themovie.presentation.viewholders

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.romanvolkov.themovie.R
import com.romanvolkov.themovie.core.common.Constants.IMAGE_URL
import com.romanvolkov.themovie.databinding.ItemMovieBinding
import com.romanvolkov.themovie.presentation.adapter.DataModel
import java.text.SimpleDateFormat
import java.util.*

class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding by viewBinding(ItemMovieBinding::bind)

    @SuppressLint("SimpleDateFormat")
    fun bind(movie: DataModel.Movie) {
        binding.apply {
            Glide.with(ivMain)
                .load("$IMAGE_URL${movie.posterPath}")
                .placeholder(R.drawable.ic_logo)
                .into(ivMain)
            tvTitle.text = movie.title
            val originalFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            val targetFormat = SimpleDateFormat("dd MMMM yyyy", Locale.US)
            val date = kotlin.runCatching {
                val fDate = originalFormat.parse(movie.date)
                targetFormat.format(fDate!!)
            }.getOrDefault("")
            tvDate.text = date
            tvDescription.text = movie.description

            container.setOnClickListener {
                movie.itemClick.invoke(movie.id)
            }
        }
    }
}