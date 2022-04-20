package com.romanvolkov.themovie.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.romanvolkov.themovie.R
import com.romanvolkov.themovie.presentation.viewholders.MovieViewHolder
import com.romanvolkov.themovie.presentation.viewholders.PaginationViewHolder

class MovieAdapter : ListAdapter<DataModel, RecyclerView.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_MOVIE -> MovieViewHolder(getView(parent, R.layout.item_movie))
            TYPE_LOADER -> PaginationViewHolder(getView(parent, R.layout.item_pagination))
            else -> throw IllegalStateException()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MovieViewHolder -> holder.bind(getItem(position) as DataModel.Movie)
            is PaginationViewHolder -> {}
        }
    }

    // инфлейтим вьюхолдер
    private fun getView(parent: ViewGroup, layout: Int): View {
        return LayoutInflater.from(parent.context).inflate(layout, parent, false)
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataModel.Movie -> TYPE_MOVIE
            is DataModel.Pagination -> TYPE_LOADER
        }
    }

    companion object {
        const val TYPE_MOVIE = 1
        const val TYPE_LOADER = 2
    }
}