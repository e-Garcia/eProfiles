package dev.egarcia.eprofiles.profiles.viewmodel

import dev.egarcia.eprofiles.profiles.model.User

data class ProfileViewState(
    val isLoading: Boolean = false,
    val userProfiles: List<User> = listOf(),
    val profileConfig: List<String> = listOf()
)