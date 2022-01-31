package dev.egarcia.eprofiles.profiles.service.repository

import dev.egarcia.eprofiles.profiles.service.model.ProfileConfigNetworkResponse
import dev.egarcia.eprofiles.profiles.service.model.UserNetworkResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepository @Inject constructor(
    private val profileApi: ProfileApi
) {
    suspend fun getUsers(): UserNetworkResponse = profileApi.getUsers()
    suspend fun getConfig(): ProfileConfigNetworkResponse = profileApi.getProfileConfig()
}