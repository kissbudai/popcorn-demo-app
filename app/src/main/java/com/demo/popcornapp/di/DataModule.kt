package com.demo.popcornapp.di

import com.demo.popcornapp.BuildConfig
import com.demo.popcornapp.data.remote.MovieRemoteSource
import com.demo.popcornapp.data.service.MovieService
import com.demo.popcornapp.data.usecase.GetGenreListUseCase
import com.demo.popcornapp.data.usecase.GetMoviesForQueryUseCase
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Returns the Koin modules needed to setup the data layer.
 */
fun createDataModules(baseUrl: String): List<Module> = createNetworkingModule(baseUrl) + apiModule + remoteModule + useCaseModule

private fun createNetworkingModule(baseUrl: String) = module {

    single<Moshi> { Moshi.Builder().build() }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().apply {
                        if (BuildConfig.DEBUG) {
                            level = HttpLoggingInterceptor.Level.BODY
                        }
                    })
                    .build()
            )
            .build()
    }
}

private val apiModule = module {
    factory { get<Retrofit>().create(MovieService::class.java) }
}

private val remoteModule = module {
    factory { MovieRemoteSource(get()) }
}

private val useCaseModule = module {
    factory { GetMoviesForQueryUseCase(get()) }
    factory { GetGenreListUseCase() }
}