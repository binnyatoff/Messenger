package ru.binnyatoff.messenger.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.binnyatoff.messenger.ui.screens.MainSharedPreferences

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideMainSharedPreferences(@ApplicationContext applicationContext: Context): MainSharedPreferences {
        return MainSharedPreferences(applicationContext)
    }
}