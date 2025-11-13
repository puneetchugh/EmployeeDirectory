package com.example.employeedirectory.di

import com.example.employeedirectory.data.ApiService
import com.example.employeedirectory.data.EmployeesRepositoryImpl
import com.example.employeedirectory.domain.EmployeesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providesRepository(
        apiService: ApiService,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): EmployeesRepository {
        return EmployeesRepositoryImpl(apiService, ioDispatcher)
    }
}