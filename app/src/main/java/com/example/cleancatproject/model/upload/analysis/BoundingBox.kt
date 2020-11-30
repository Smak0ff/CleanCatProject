package com.example.cleancatproject.model.upload.analysis

import com.google.gson.annotations.SerializedName

data class BoundingBox(
    @SerializedName("Width") val width: Double,
    @SerializedName("Height") val height: Double,
    @SerializedName("Left") val left: Double,
    @SerializedName("Top") val top: Double
)