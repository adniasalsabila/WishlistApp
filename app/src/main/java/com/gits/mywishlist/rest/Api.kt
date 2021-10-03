package com.gits.mywishlist.rest

import com.gits.mywishlist.model.DefaultResponse
import com.gits.mywishlist.model.GetListResponse
import com.gits.mywishlist.model.ImageResponse
import com.gits.mywishlist.model.LoginResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface Api {

    @FormUrlEncoded
    @POST("restApi.php")
    fun insertWishlist(
        @Field("id") id: String,
        @Field("nameItem") nameItem: String,
        @Field("cost") cost: Int,
        @Query("function") function: String
    ) : Call<DefaultResponse>

    @FormUrlEncoded
    @POST("restApi.php")
    fun updateWishlist(
        @Field("id") id: String,
        @Field("nameItem") nameItem: String,
        @Field("cost") cost: Int,
        @Query("function") function: String
    ) : Call<DefaultResponse>

    @GET("restApi.php?function=get_wishlist")
    fun getListWishlist(): Call<GetListResponse>

    @DELETE("restApi.php?function=delete_wishlist")
    fun deleteWishlist(): Call<DefaultResponse>

    //user
    @FormUrlEncoded
    @POST("restApi.php")
    fun addUser(
        @Field("id_user") id: String,
        @Field("nama_user") namaUser: String,
        @Field("email_user") emailUser: String,
        @Field("username_user") usernameUser: String,
        @Field("password_user") passwordUser: String,
        @Query("function") function: String
    ) : Call<DefaultResponse>

    @FormUrlEncoded
    @POST("restApi.php")
    fun editUser(
        @Field("id_user") id: String,
        @Field("nama_user") namaUser: String,
        @Field("email_user") emailUser: String,
        @Field("username_user") usernameUser: String,
        @Field("password_user") passwordUser: String,
        @Query("function") function: String
    ) : Call<DefaultResponse>

    @GET("apilogin.php?function=login_user")
    fun loginUser(
        @Query("email_user") email_user: String,
        @Query("password_user") password_user: String
    ): Call<LoginResponse>

    @Multipart
    @POST("image/create")
    suspend fun addImage(@Part photo: MultipartBody.Part) : ImageResponse
}