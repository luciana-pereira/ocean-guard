
package com.greenconect.oceanguard.data.model

data class UserEmail(
    val email: String,
    val password: String
)

data class UserPassword(
    val password: String
)

data class AuthResponse(
    val access_token: String,
    val token_type: String,
    val expires_in: Int,
    val refresh_token: String
)

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
