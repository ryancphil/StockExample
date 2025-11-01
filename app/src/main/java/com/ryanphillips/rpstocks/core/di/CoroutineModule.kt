package com.ryanphillips.rpstocks.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

/**
 * Injecting Dispatchers enables coroutine testability.
 *
 * Creating and injecting a custom Application Scope is useful
 * for longer running jobs that need to outlive other scopes like
 * viewModelScope and lifecycleScope.
 */
@Module
@InstallIn(SingletonComponent::class)
object CoroutineModule {

    // Coroutine Dispatchers
    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class IoDispatcher

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class DefaultDispatcher

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class MainDispatcher

    @Provides
    @IoDispatcher
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @DefaultDispatcher
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @Provides
    @MainDispatcher
    fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    // Custom Application Coroutine Scope
    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class ApplicationScope

    @Provides
    @Singleton
    @ApplicationScope
    fun provideApplicationScope(@MainDispatcher dispatcher: CoroutineDispatcher): CoroutineScope {
        return CoroutineScope(SupervisorJob() + dispatcher)
    }
}