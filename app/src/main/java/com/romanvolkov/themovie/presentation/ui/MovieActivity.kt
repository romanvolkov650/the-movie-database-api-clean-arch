package com.romanvolkov.themovie.presentation.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.romanvolkov.themovie.R
import com.romanvolkov.themovie.common.Toast
import com.romanvolkov.themovie.common.ToastConstants.TYPE_FAIL
import com.romanvolkov.themovie.core.Action
import com.romanvolkov.themovie.core.NoInternetException
import com.romanvolkov.themovie.core.common.Constants.STATUS_OK
import com.romanvolkov.themovie.databinding.ActivityMovieBinding
import com.romanvolkov.themovie.presentation.adapter.DataModel
import com.romanvolkov.themovie.presentation.adapter.MovieAdapter
import com.romanvolkov.themovie.presentation.model.PopularState
import com.romanvolkov.themovie.presentation.viewmodel.MainViewModel
import com.romanvolkov.themovie.presentation.viewmodel.MovieActions
import com.uber.autodispose.android.lifecycle.scope
import com.uber.autodispose.autoDispose

class MovieActivity : AppCompatActivity() {

    private val model: MainViewModel by viewModels()
    private val binding by viewBinding(ActivityMovieBinding::bind)
    private val dataAdapter: MovieAdapter by lazy { MovieAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)
        binding.rvMovies.apply {
            hasFixedSize()
            layoutManager = LinearLayoutManager(this@MovieActivity)
            adapter = dataAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    binding.fabScrollTop.isVisible = canScrollVertically(-1)
                    if (!model.state.value.isPageLoading) {
                        if ((layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                            == model.state.value.movies?.size?.minus(2)
                        ) {
                            model.dispatch { MovieActions.GetNextPopular }
                        }
                    }
                }
            })
        }

        binding.placeholderContainer.setClickButton {
            model.dispatch { MovieActions.GetPopular(page = model.state.value.currentPage) }
        }

        binding.fabScrollTop.setOnClickListener {
            binding.rvMovies.smoothScrollToPosition(0)
        }
        model.dispatch { MovieActions.GetPopular(page = 1) }

        model.state.observe(this) {
            render(it)
        }
    }

    // отрисовать интерфейс
    private fun render(state: PopularState) {
        binding.pbLoading.isVisible = model.state.value.isLoading
        if (model.state.value.status == STATUS_OK) {
            binding.placeholderContainer.isVisible = false
            binding.rvMovies.isVisible = !model.state.value.isLoading
        } else {
            binding.placeholderContainer.isVisible = true
        }
        val viewHolderArray = ArrayList<DataModel>()
        state.movies?.forEach { popular ->
            viewHolderArray.add(
                DataModel.Movie(
                    id = popular.id,
                    posterPath = popular.posterPath,
                    title = popular.title,
                    date = popular.releaseDate ?: "",
                    description = popular.overview,
                    itemClick = { id ->
                        openMovie(id)
                    }
                )
            )
        }
        if (state.hasNext) viewHolderArray.add(DataModel.Pagination(-1))
        dataAdapter.submitList(viewHolderArray)
    }

    private fun openMovie(movieId: Int) {
        MovieDetailsFragment.newInstance(movieId)
            .apply {
                setStyle(DialogFragment.STYLE_NORMAL, R.style.ExpandDialogStyleCustom)
                show(supportFragmentManager, "movie_detail_$movieId")
            }
    }

    override fun onResume() {
        super.onResume()
        model.listen<Action.Error>().autoDispose(scope()).subscribe {
            when (it.error) {
                is NoInternetException -> {
                    if (model.state.value.movies?.isEmpty() == false) {
                        Toast.show(R.string.no_internet_connection, TYPE_FAIL)
                    }
                }
            }
        }
    }
}