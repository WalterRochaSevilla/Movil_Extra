package com.calyrsoft.ucbp1.features.movie.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.calyrsoft.ucbp1.features.movie.domain.model.MovieModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(movie: MovieModel, back: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Movie Details")
                },
                navigationIcon = {
                    IconButton(
                        onClick = back,
                        content = {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    )
                }
            )
        },
        content = {
                paddingValues ->
            Column(
                modifier = Modifier.padding(paddingValues)
            ) {
                Text(text = movie.title)
                Text(text = movie.overview)
            }
        }
    )


}