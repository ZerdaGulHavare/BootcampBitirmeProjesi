package com.example.foodapp.di

import com.example.foodapp.data.datasource.FoodDataSource
import com.example.foodapp.data.repo.FoodRepository
import com.example.foodapp.retrofit.ApiUtils
import com.example.foodapp.retrofit.UrunlerDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideFoodRepository(foodDataSource: FoodDataSource): FoodRepository {
        return FoodRepository(foodDataSource)
    }

    @Provides
    @Singleton
    fun provideFoodDataSource(urunlerDao: UrunlerDao): FoodDataSource {
        return FoodDataSource(urunlerDao)
    }

    @Provides
    @Singleton
    fun provideUrunlerDao(): UrunlerDao {
        return ApiUtils.getUrunlerDao()
    }
}
