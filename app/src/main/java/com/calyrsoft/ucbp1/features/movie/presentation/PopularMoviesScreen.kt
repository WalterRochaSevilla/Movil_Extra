package com.calyrsoft.ucbp1.features.movie.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.calyrsoft.ucbp1.features.movie.domain.model.MovieModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun PopularMoviesScreen(
    popularMoviesViewModel: PopularMoviesViewModel = koinViewModel(),
    navigateToDetail: (movie: MovieModel) -> Unit
) {
    val state = popularMoviesViewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        popularMoviesViewModel.fetchPopularMovies()
    }

    when (val s = state.value) {
        is PopularMoviesViewModel.UiState.Error -> {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(s.message)
            }
        }
        is PopularMoviesViewModel.UiState.Loading ->
            CircularProgressIndicator()
        is PopularMoviesViewModel.UiState.Success ->
            PopularMoviesView(movies = s.movies, navigateToDetail = navigateToDetail)

    }

}