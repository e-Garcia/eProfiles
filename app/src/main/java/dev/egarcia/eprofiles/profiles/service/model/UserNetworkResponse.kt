package dev.egarcia.eprofiles.profiles.service.model

import com.google.gson.annotations.SerializedName
import dev.egarcia.eprofiles.profiles.model.User

data class UserNetworkResponse(
    @SerializedName("users")
    val users: List<User>
)