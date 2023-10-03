package com.example.submissionfundamental1.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class UserResponse(

	@field:SerializedName("total_count")
	val totalCount: Int,

	@field:SerializedName("incomplete_results")
	val incompleteResults: Boolean,

	@field:SerializedName("items")
	val items: MutableList<Item>
) {
	@Parcelize
	@Entity(tableName = "user")
	data class Item(
		@ColumnInfo(name = "avatar_url")
		val avatar_url : String,
		@PrimaryKey
		val id : Int,
		@ColumnInfo(name = "login")
		val login : String
	) : Parcelable
}