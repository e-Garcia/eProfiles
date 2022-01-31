package dev.egarcia.eprofiles.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.egarcia.eprofiles.BASE_URL
import dev.egarcia.eprofiles.profiles.service.repository.ProfileApi
import dev.egarcia.eprofiles.profiles.service.repository.ProfileRepository
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun providesOKHTTPClient(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        return httpClient.build()
    }

    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson =
        GsonBuilder().create()

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit.Builder =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))

    @Singleton
    @Provides
    fun provideProfileApi(retrofit: Retrofit.Builder): ProfileApi =
        retrofit.build().create(ProfileApi::class.java)

    @Singleton
    @Provides
    fun provideProfileRepository(profileApi: ProfileApi): ProfileRepository =
        ProfileRepository(profileApi)

}