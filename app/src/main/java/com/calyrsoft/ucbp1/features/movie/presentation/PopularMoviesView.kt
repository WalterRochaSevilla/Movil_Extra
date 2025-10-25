package com.calyrsoft.ucbp1.features.movie.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.calyrsoft.ucbp1.features.movie.domain.model.MovieModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun PopularMoviesView( movies: List<MovieModel>, viewModel: PopularMoviesViewModel = koinViewModel(),
                       navigateToDetail: (movie: MovieModel) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(movies.size) {
            CardMovie(movie = movies[it], viewModel, navigateToDetail)
        }
    }
}

@Composable
fun CardMovie(movie: MovieModel, viewModel: PopularMoviesViewModel, navigateToDetail: (MovieModel) -> Unit) {
    OutlinedCard(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxSize(),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
        elevation = androidx.compose.material3.CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = movie.pathUrl.value,
                contentDescription = movie.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(0.7f)
            )

            Text(
                text = movie.title,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(),
                maxLines = 2
            )
            StarRating(rating = movie.rating, onRatingChanged = {
                viewModel.rateMovie(movie.id, it)
            })
            TextButton(onClick = {
                navigateToDetail(movie)
            }) {
                Text("Ver detalle")
            }
        }
    }
}

@Composable
fun StarRating(
    rating: Int, // de 0 a 5
    onRatingChanged: (Int) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 1..5) {
            IconButton(onClick = { onRatingChanged(i) }) {
                Icon(
                    imageVector = if (i <= rating) Icons.Filled.Star else Icons.Outlined.Star,
                    contentDescription = "$i estrellas",
                    tint = if (i <= rating) Color(0xFFFFC107) else Color.Gray, // amarillo y gris
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}