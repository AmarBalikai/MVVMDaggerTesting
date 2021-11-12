package com.code.mvvmdaggertesting.di

import android.content.Context
import com.code.mvvmdaggertesting.networking.LoginAPIService
import com.code.mvvmdaggertesting.networking.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun providesApi(remoteDataSource: RemoteDataSource): LoginAPIService{
        return remoteDataSource.buildApi(LoginAPIService::class.java)
    }
}