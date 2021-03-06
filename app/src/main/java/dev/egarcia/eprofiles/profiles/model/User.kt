package dev.egarcia.eprofiles.profiles.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("gender")
    val gender: String?,
    @SerializedName("about")
    val about: String?,
    @SerializedName("school")
    val school: String?,
    @SerializedName("hobbies")
    val hobbies: List<String>?,
    @SerializedName("photo")
    val photo: String?
)