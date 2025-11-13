package com.example.employeedirectory.di

import com.example.employeedirectory.domain.EmployeesRepository
import com.example.employeedirectory.domain.EmployeesUseCase
import com.example.employeedirectory.domain.EmployeesUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCase {

    @Provides
    @Singleton
    fun provideUseCaseWithFlow(employeesRepository: EmployeesRepository): EmployeesUseCase {
        return EmployeesUseCaseImpl(employeesRepository = employeesRepository)
    }
}