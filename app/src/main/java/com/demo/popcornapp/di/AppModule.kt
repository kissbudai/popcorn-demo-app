package com.demo.popcornapp.di

import com.demo.popcornapp.BuildConfig
import com.demo.popcornapp.data.local.SharedPreferences
import com.demo.popcornapp.feature.detail.MovieDetailViewModel
import com.demo.popcornapp.feature.home.HomeViewModel
import com.demo.popcornapp.feature.result.MovieResultViewModel
import com.demo.popcornapp.feature.uimodel.MovieUiModel
import com.demo.popcornapp.utils.AndroidClock
import com.demo.popcornapp.utils.Clock
import com.demo.popcornapp.utils.DateHandler
import com.demo.popcornapp.shared.MovieTagBuilder
import com.demo.popcornapp.utils.StringLookUpImpl
import com.demo.popcornapp.utils.StringLookup
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

fun createAppModules(): List<Module> = createDataModules(BuildConfig.BASE_URL) + appModules

private val homeModule = module {
    viewModel { HomeViewModel(get(), get(), get()) }
}

private val resultModule = module {
    viewModel { (query: String, movies: List<MovieUiModel>) -> MovieResultViewModel(query, movies, get(), get()) }
}

private val detailModule = module {
    viewModel { (movie: MovieUiModel) -> MovieDetailViewModel(movie, get(), get()) }
}

private val appModule = module {
    factory<StringLookup> { StringLookUpImpl(androidContext()) }
    factory<Clock> { AndroidClock() }
    factory { MovieTagBuilder(get(), get(), get(), get()) }
    single { SharedPreferences.create(androidContext()) }
    factory { DateHandler() }
}

private val appModules = listOf(appModule, homeModule, resultModule, detailModule)