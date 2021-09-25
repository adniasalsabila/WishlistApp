package com.gits.mywishlist.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GetListResponse(

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
) : Parcelable

@Parcelize
data class DataItem(

	@field:SerializedName("cost")
	val cost: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("nameItem")
	val nameItem: String? = null
) : Parcelable
