package com.gits.mywishlist.model

import com.google.gson.annotations.SerializedName

data class ImageResponse(

    @field:SerializedName("status")
    val code: Int,

    @field:SerializedName("data")
    val data: Data,

    @field:SerializedName("message")
    val message: String
)

data class Data(

    @field:SerializedName("nama_image")
    val fileName: String,

    @field:SerializedName("id_image")
    val id: Int,

    @field:SerializedName("file_image")
    val url: String
)
