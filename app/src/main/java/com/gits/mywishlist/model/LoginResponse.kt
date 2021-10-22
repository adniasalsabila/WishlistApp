package com.gits.mywishlist.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @field:SerializedName("status")
    val code: Int,

    @field:SerializedName("data")
    val data: LoginData,

    @field:SerializedName("message")
    val message: String
)

data class LoginData(
    @field:SerializedName("email_user")
    val emailUser: String,

    @field:SerializedName("password_user")
    val passwordUser: String
)