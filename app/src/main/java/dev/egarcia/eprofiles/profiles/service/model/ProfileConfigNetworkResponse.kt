package dev.egarcia.eprofiles.profiles.service.model

import com.google.gson.annotations.SerializedName

data class ProfileConfigNetworkResponse(
    @SerializedName("profile")
    val profileList: List<String>
)