package com.stdio.oauthsample.di

import com.stdio.oauthsample.common.AccountSession
import com.stdio.oauthsample.data.*
import com.stdio.oauthsample.presentation.viewmodel.MainViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val viewModelModule = module {
    viewModel { MainViewModel(get()) }
}

val appModule = module {

    fun providesAuthUrl(): String = "https://oauth.vk.com/"

    fun providesBaseUrl(): String = "https://api.vk.com/"

    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    fun provideHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient.Builder {
        return OkHttpClient.Builder()
            .callTimeout(1, TimeUnit.MINUTES)
            .retryOnConnectionFailure(true)
            .addInterceptor(HostSelectionInterceptor())
            .addInterceptor(OAuthInterceptor())
            .addInterceptor(httpLoggingInterceptor)
    }

    fun provideRetrofit(BASE_URL: String, httpBuilder: OkHttpClient.Builder): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(httpBuilder.build())
            .build()

    fun provideMainService(retrofit: Retrofit): MainService =
        retrofit.create(MainService::class.java)

    fun provideRemoteDataSource(mainService: MainService) =
        RemoteDataSource(mainService)

    fun provideRepository(remoteDataSource: RemoteDataSource) =
        MainRepository(remoteDataSource)

    single(named("auth_url")) { providesAuthUrl() }
    single(named("base_url")) { providesBaseUrl() }
    single { provideHttpLoggingInterceptor() }
    factory { provideHttpClient(get()) }
    single {
        provideRetrofit(get(qualifier = named("auth_url")), get())
    }
    single { provideMainService(get()) }
    single { provideRemoteDataSource(get()) }
    single { provideRepository(get()) }
}

val allModules = viewModelModule + appModule