
package com.greenconect.oceanguard.data.model

import kotlinx.serialization.Serializable


@Serializable
data class UserEmail(
    val email: String,
    val password: String
)

@Serializable
data class UserPassword(
    val password: String
)

@Serializable
data class AuthResponse(
    val access_token: String,
    val token_type: String,
    val expires_in: Int,
    val refresh_token: String
)

@Serializable
data class User(
    val id: String? = null,
    val name: String,
    val surname: String,
    val email: String,
    val password: String,
    val biological_sex: String?,
    val birth_date: String?,
    val profile_image: String?
)
