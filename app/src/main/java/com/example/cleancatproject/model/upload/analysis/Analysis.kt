package com.example.cleancatproject.model.upload.analysis

import com.google.gson.annotations.SerializedName

data class Analysis(
    @SerializedName("labels") val labels: List<Labels>,
    @SerializedName("moderation_labels") val moderation_labels: List<String>,
    @SerializedName("vendor") val vendor: String,
    @SerializedName("image_id") val image_id: String,
    @SerializedName("created_at") val created_at: String
)