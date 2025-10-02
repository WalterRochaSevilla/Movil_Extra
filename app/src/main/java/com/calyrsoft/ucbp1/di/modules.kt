package com.calyrsoft.ucbp1.di

import com.calyrsoft.ucbp1.R
import com.calyrsoft.ucbp1.features.dollar.data.database.AppRoomDatabase
import com.calyrsoft.ucbp1.features.dollar.data.datasource.DollarLocalDataSource
import com.calyrsoft.ucbp1.features.dollar.data.repository.DollarRepository
import com.calyrsoft.ucbp1.features.dollar.data.datasource.RealTimeRemoteDataSource
import com.calyrsoft.ucbp1.features.dollar.domain.repository.IDollarRepository
import com.calyrsoft.ucbp1.features.dollar.domain.usecase.FetchDollarParallelUseCase
import com.calyrsoft.ucbp1.features.dollar.domain.usecase.FetchDollarUseCase
import com.calyrsoft.ucbp1.features.dollar.presentation.DollarViewModel
import com.calyrsoft.ucbp1.features.github.data.api.GithubService
import com.calyrsoft.ucbp1.features.github.data.datasource.GithubRemoteDataSource
import com.calyrsoft.ucbp1.features.github.data.repository.GithubRepository
import com.calyrsoft.ucbp1.features.github.domain.repository.IGithubRepository
import com.calyrsoft.ucbp1.features.github.domain.usecase.FindByNickNameUseCase
import com.calyrsoft.ucbp1.features.github.presentation.GithubViewModel
import com.calyrsoft.ucbp1.features.movie.data.api.MovieService
import com.calyrsoft.ucbp1.features.movie.data.datasource.MovieLocalDataSource
import com.calyrsoft.ucbp1.features.movie.data.datasource.MovieRemoteDataSource
import com.calyrsoft.ucbp1.features.movie.data.repository.MovieRepository
import com.calyrsoft.ucbp1.features.movie.domain.repository.IMoviesRepository
import com.calyrsoft.ucbp1.features.movie.domain.usecase.FetchPopularMoviesUseCase
import com.calyrsoft.ucbp1.features.movie.domain.usecase.RateMovieUseCase
import com.calyrsoft.ucbp1.features.movie.presentation.PopularMoviesViewModel
import com.calyrsoft.ucbp1.features.profile.application.ProfileViewModel
import com.calyrsoft.ucbp1.features.profile.data.repository.ProfileRepository
import com.calyrsoft.ucbp1.features.profile.domain.repository.IProfileRepository
import com.calyrsoft.ucbp1.features.profile.domain.usecase.GetProfileUseCase
import com.calyrsoft.ucbp1.navigation.NavigationViewModel
import okhttp3.OkHttpClient
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkConstants {
    const val RETROFIT_GITHUB = "RetrofitGithub"
    const val GITHUB_BASE_URL = "https://api.github.com/"
    const val RETROFIT_MOVIE = "RetrofitMovie"
    const val MOVIE_BASE_URL = "https://api.themoviedb.org/"
}

val appModule = module {


    // OkHttpClient
    single {
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    // Retrofit
    single(named(NetworkConstants.RETROFIT_GITHUB)) {
        Retrofit.Builder()
            .baseUrl(NetworkConstants.GITHUB_BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Retrofit
    single(named(NetworkConstants.RETROFIT_MOVIE)) {
        Retrofit.Builder()
            .baseUrl(NetworkConstants.MOVIE_BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // GithubService
    single<GithubService> {
        get<Retrofit>( named(NetworkConstants.RETROFIT_GITHUB)).create(GithubService::class.java)
    }
    single{ GithubRemoteDataSource(get()) }
    single<IGithubRepository>{ GithubRepository(get()) }

    factory { FindByNickNameUseCase(get()) }
    viewModel { GithubViewModel(get(), get()) }

    single<IProfileRepository> { ProfileRepository() }
    factory { GetProfileUseCase(get()) }
    viewModel { ProfileViewModel(get()) }

    single { AppRoomDatabase.getDatabase(get()) }
    single(named("dollarDao")) { get<AppRoomDatabase>().dollarDao() }
    single { RealTimeRemoteDataSource() }
    single { DollarLocalDataSource(get(named("dollarDao"))) }
    single<IDollarRepository> { DollarRepository(get(), get()) }
    factory { FetchDollarUseCase(get()) }
    factory { FetchDollarParallelUseCase(get()) }
    viewModel{ DollarViewModel(get(), get()) }


    single(named("apiKey")) {
        androidApplication().getString(R.string.api_key)
    }

    single<MovieService> {
        get<Retrofit>(named(NetworkConstants.RETROFIT_MOVIE)).create(MovieService::class.java)
    }
    single(named("movieDao")) { get<AppRoomDatabase>().movieDao() }
    single { MovieRemoteDataSource(get(), get(named("apiKey"))) }
    single { MovieLocalDataSource(get(named("movieDao"))) }
    single<IMoviesRepository> { MovieRepository(get(), get()) }
    factory { FetchPopularMoviesUseCase(get()) }
    factory { RateMovieUseCase(get()) }
    viewModel{ PopularMoviesViewModel(get(), get()) }

    viewModel { NavigationViewModel() }
}