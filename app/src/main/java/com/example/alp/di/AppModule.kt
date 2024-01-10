package com.example.alp.di

import com.example.alp.core.repository.AuthRepositoryImpl
import com.example.alp.core.repository.FinanceRepositoryImpl
import com.example.alp.core.repository.ProfileRepositoryImpl
import com.example.alp.core.repository.TokenPreferencesRepositoryImpl
import com.example.alp.core.repository.contract.AuthRepository
import com.example.alp.core.repository.contract.FinanceRepository
import com.example.alp.core.repository.contract.ProfileRepository
import com.example.alp.core.repository.contract.TokenPreferencesRepository
import com.example.alp.core.source.local.TokenPreferences
import com.example.alp.core.source.remote.services.AuthService
import com.example.alp.core.source.remote.services.FinanceService
import com.example.alp.core.source.remote.services.ProfileService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(includes = [NetworkModule::class, DatastoreModule::class])
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providesTokenPreferencesRepository(
        pref: TokenPreferences
    ): TokenPreferencesRepository {
        return TokenPreferencesRepositoryImpl(pref)
    }

    @Provides
    @Singleton
    fun providesAuthRepository(api: AuthService): AuthRepository {
        return AuthRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun providesFinanceRepository(api: FinanceService): FinanceRepository {
        return FinanceRepositoryImpl(api)
    }
    @Provides
    @Singleton
    fun providesProfileRepository(api: ProfileService): ProfileRepository {
        return ProfileRepositoryImpl(api)
    }
}