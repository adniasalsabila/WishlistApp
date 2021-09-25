package com.gits.mywishlist.rest

import com.gits.mywishlist.model.DefaultResponse
import com.gits.mywishlist.model.GetListResponse
import retrofit2.Call
import retrofit2.http.*

interface Api {

    @FormUrlEncoded
    @POST("restApi.php")
    fun insertWishlist(
        @Field("id") id: Int,
        @Field("nameItem") nameItem: String,
        @Field("cost") cost: Int,
        @Query("function") function: String
    ) : Call<DefaultResponse>

    @FormUrlEncoded
    @POST("restApi.php?function=update_wishlist{id}")
    fun updateWishlist(
        @Field("nameItem") nameItem: String,
        @Field("cost") cost: Int,
        @Path("id") id: Int
    ) : Call<DefaultResponse>

    @GET("restApi.php?function=get_wishlist")
    fun getListWishlist(): Call<GetListResponse>

    @DELETE("restApi.php?function=delete_wishlist")
    fun deleteWishlist(): Call<DefaultResponse>
}