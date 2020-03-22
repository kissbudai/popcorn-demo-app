package com.demo.popcornapp.di

import com.demo.popcornapp.BuildConfig
import com.demo.popcornapp.feature.home.HomeViewModel
import com.demo.popcornapp.feature.result.MovieResultViewModel
import com.demo.popcornapp.feature.uimodel.MovieUiModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

fun createAppModules(): List<Module> = createDataModules(BuildConfig.BASE_URL) + appModules

private val homeModule = module {
    viewModel { HomeViewModel(get()) }
}

private val resultModule = module {
    viewModel { (query: String, movies: List<MovieUiModel>) -> MovieResultViewModel(query, movies) }
}

private val appModules = listOf(homeModule, resultModule)