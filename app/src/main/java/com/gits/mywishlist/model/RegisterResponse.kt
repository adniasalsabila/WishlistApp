package com.gits.mywishlist.model

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

    @field:SerializedName("status")
    val code: Int,

    @field:SerializedName("data")
    val data: DataRegister,

    @field:SerializedName("message")
    val message: String
)

data class DataRegister(

    @field:SerializedName("nama_user")
    val namaUser: String,

    @field:SerializedName("email_user")
    val emailUser: String,

    @field:SerializedName("username_user")
    val usernameUser: String,

    @field:SerializedName("password_user")
    val passwordUser: String,

    @field:SerializedName("id_image")
    val idImage: String,

    @field:SerializedName("image")
    val image: String
)
