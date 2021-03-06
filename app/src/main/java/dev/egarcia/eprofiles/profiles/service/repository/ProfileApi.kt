package dev.egarcia.eprofiles.profiles.service.repository

import dev.egarcia.eprofiles.profiles.service.model.ProfileConfigNetworkResponse
import dev.egarcia.eprofiles.profiles.service.model.UserNetworkResponse
import retrofit2.http.GET

interface ProfileApi {
    @GET("users")
    suspend fun getUsers(): UserNetworkResponse

    @GET("config")
    suspend fun getProfileConfig(): ProfileConfigNetworkResponse
}