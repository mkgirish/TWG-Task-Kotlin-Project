package com.campgladiator.injection


import android.app.Application

import android.content.Context
import android.content.SharedPreferences
import com.example.twgproducttask.facades.SharedPreferencesFacade
import com.example.twgproducttask.repository.UserRepository
import com.example.twgproducttask.network.interceptors.service.WarehouseService
import com.example.twgproducttask.repository.ProductRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/*
 This class is used to provide SharedPreferences, UserRepository, ProductRepository and SharedPreferencesFacade
  dependecy injection
*/

@Module
class AndroidModule(private val application: Application) {

    @Provides
    @Singleton
    fun provideApplicationContext(): Context = application

    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context): SharedPreferences = context.getSharedPreferences("USER", Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideUserRepository(preferencesFacade: SharedPreferencesFacade,wareHouseService: WarehouseService): UserRepository {
        return UserRepository(preferencesFacade, wareHouseService)
    }

    @Provides
    @Singleton
    fun provideProductRepository(wareHouseService: WarehouseService): ProductRepository {
        return ProductRepository(wareHouseService)
    }





    @Provides
    @Singleton
    fun provideSharedPreferencesFacade(sharedPreferences: SharedPreferences): SharedPreferencesFacade {
        return SharedPreferencesFacade(sharedPreferences)
    }

}