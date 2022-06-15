package ru.binnyatoff.messenger.di


import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.binnyatoff.messenger.data.network.Api
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val httpClient = OkHttpClient.Builder()
            //.addInterceptor()
            .build()

        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): Api = retrofit.create(Api::class.java)
}