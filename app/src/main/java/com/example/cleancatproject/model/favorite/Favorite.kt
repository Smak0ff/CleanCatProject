package com.example.cleancatproject.model.favorite

data class Favorite(
	val id: Int,
	val user_id: String,
	val image_id: String,
	val sub_id: String,
	val created_at: String,
	val image: Image
)