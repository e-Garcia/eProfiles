package dev.egarcia.eprofiles.profiles.service.model

import com.google.gson.annotations.SerializedName
import dev.egarcia.eprofiles.profiles.model.User
import dev.egarcia.eprofiles.profiles.model.isValidResponse

data class UserNetworkResponse(
    @SerializedName("users")
    val users: List<User>
)
fun UserNetworkResponse.isValidResponse() : Boolean {
    return users.all { it.isValidResponse() }
}