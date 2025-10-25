package com.calyrsoft.ucbp1.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.calyrsoft.ucbp1.features.cardexample.presentation.CardScreen
import com.calyrsoft.ucbp1.features.dollar.presentation.DollarScreen
import com.calyrsoft.ucbp1.features.github.presentation.GithubScreen
import com.calyrsoft.ucbp1.features.movie.domain.model.MovieModel
import com.calyrsoft.ucbp1.features.movie.presentation.MovieDetailScreen
import com.calyrsoft.ucbp1.features.movie.presentation.PopularMoviesScreen
import com.calyrsoft.ucbp1.features.profile.application.ProfileScreen
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import java.net.URLDecoder
import java.net.URLEncoder

@Composable
fun AppNavigation(navigationViewModel: NavigationViewModel, modifier: Modifier, navController: NavHostController) {

    // Manejar navegación desde el ViewModel
    LaunchedEffect(Unit) {
        navigationViewModel.navigationCommand.collect { command ->
            when (command) {
                is NavigationViewModel.NavigationCommand.NavigateTo -> {
                    navController.navigate(command.route) {
                        // Configuración del back stack según sea necesario
                        when (command.options) {
                            NavigationOptions.CLEAR_BACK_STACK -> {
                                popUpTo(0) // Limpiar todo el back stack
                            }
                            NavigationOptions.REPLACE_HOME -> {
                                popUpTo(Screen.Dollar.route) { inclusive = true }
                            }
                            else -> {
                                // Navegación normal
                            }
                        }
                    }
                }
                is NavigationViewModel.NavigationCommand.PopBackStack -> {
                    navController.popBackStack()
                }
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = Screen.PopularMovies.route,
        modifier = modifier
    ) {
        composable(Screen.Github.route) {
            GithubScreen(modifier = Modifier)
        }
        composable(Screen.Home.route) {

        }
        composable(Screen.Profile.route) {
            ProfileScreen()
        }

        composable(Screen.CardExamples.route) { CardScreen() }

        composable(Screen.Dollar.route) {
            DollarScreen()
        }

        composable(Screen.PopularMovies.route) {
            PopularMoviesScreen( navigateToDetail  = { movie ->
                val movieJson = Json.encodeToString(movie)
                val encodeMovieJson = URLEncoder.encode(movieJson, "UTF-8")

                navController.navigate(
                    "${Screen.MovieDetail.route}/${encodeMovieJson}")
            })
        }

        composable(
            route = "${Screen.MovieDetail.route}/{movie}",
            arguments = listOf(
                navArgument("movie") { type = NavType.StringType }
            )
        ) {
            val movieJson = it.arguments?.getString("movie") ?: ""
            val movieDecoded = URLDecoder.decode(movieJson, "UTF-8")
            val movie = Json.decodeFromString<MovieModel>(movieDecoded)

            MovieDetailScreen(
                movie = movie,
                back = {
                    navController.popBackStack()
                })
        }
    }


}