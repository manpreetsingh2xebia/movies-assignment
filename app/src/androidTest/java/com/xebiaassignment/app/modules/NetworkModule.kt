package com.xebiaassignment.app.modules

import com.xebiaassignment.data.data_source.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@TestInstallIn(components = [SingletonComponent::class], replaces = [NetworkModule::class])
@Module
object NetworkModuleTest {

    @Singleton
    @Provides
    fun provideMockkServer(): MockWebServer {
        return MockWebServer()
    }

    @Singleton
    @Provides
    fun provideRetrofit(mockWebServer: MockWebServer): Retrofit {
        return Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

}