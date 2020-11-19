package com.example.cleancatproject.model.upload


data class Upload (

	val breeds : List<String>,
	val id : String,
	val url : String,
	val width : Int,
	val height : Int,
	val sub_id : String,
	val created_at : String,
	val original_filename : String,
	val breed_ids : String
)