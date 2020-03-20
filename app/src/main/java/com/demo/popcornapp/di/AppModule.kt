package com.demo.popcornapp.di

import com.demo.popcornapp.feature.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

fun createAppModules(): List<Module> = listOf(homeModule)


private val homeModule = module {
    viewModel { HomeViewModel() }
}