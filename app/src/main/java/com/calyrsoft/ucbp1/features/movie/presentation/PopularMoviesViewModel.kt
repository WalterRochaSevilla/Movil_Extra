package com.calyrsoft.ucbp1.features.movie.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.calyrsoft.ucbp1.features.movie.domain.model.MovieModel
import com.calyrsoft.ucbp1.features.movie.domain.usecase.FetchPopularMoviesUseCase
import com.calyrsoft.ucbp1.features.movie.domain.usecase.RateMovieUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PopularMoviesViewModel(
    private val fetchPopularMovies: FetchPopularMoviesUseCase,
    private val rateMovieUseCase: RateMovieUseCase
): ViewModel() {

    sealed class UiState {
        object Loading : UiState()
        data class Success(val movies: List<MovieModel>) : UiState()
        data class Error(val message: String) : UiState()
    }

    private val _state = MutableStateFlow<UiState>(UiState.Loading)
    val state: StateFlow<UiState> = _state.asStateFlow()

    fun fetchPopularMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = UiState.Loading
            val result = fetchPopularMovies.invoke()
            result.fold(
                onSuccess = {
                    _state.value = UiState.Success(it)
                },
                onFailure = {
                    _state.value = UiState.Error(it.message.toString())
                }
            )
        }
    }

    fun rateMovie(movieId: Int, rating: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = UiState.Loading
            val result = rateMovieUseCase.invoke(movieId, rating)
            result.fold(
                onSuccess = {
                   // _state.value = UiState.Success(it)
                    fetchPopularMovies()
                },
                onFailure = {
                    _state.value = UiState.Error(it.message.toString())
                }
            )
        }
    }

}