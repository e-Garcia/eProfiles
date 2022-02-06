package dev.egarcia.eprofiles.profiles.service.model

import com.google.gson.annotations.SerializedName

data class Like(
    @SerializedName("likedUserId")
    val likedUserId: Int
)