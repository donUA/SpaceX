package com.bossco.spacexclient.di

import android.app.Application
import android.content.Context
import com.bossco.spacexclient.database.AppDao
import com.bossco.spacexclient.database.AppDatabase
import com.bossco.spacexclient.network.RetroInterface
import com.bossco.spacexclient.ui.MainActivity
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by Don Muthiani on 8/5/21.
 * Copyright (c) 2021 Accuret. All rights reserved.

 */
@Module
class AppModule(private val application: Application) {

    val baseUrl = "https://api.spacexdata.com/"


    @Singleton
    @Provides
    fun provideContext(): Context {
        return application.applicationContext
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient{
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder()
        okHttpClient.addInterceptor(logging)
        return okHttpClient.build()

    }

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(provideOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideRetroInstance(): RetroInterface{
        return provideRetrofit().create(RetroInterface::class.java)
    }

    @Singleton
    @Provides
    fun provideAppDatabase(): AppDatabase{
        return AppDatabase.getDbInstance(provideContext())
    }

    @Singleton
    @Provides
    fun getAppDao(): AppDao{
        return provideAppDatabase().getDao()
    }


}