package com.example.cleancatproject.model.upload.analysis

import com.google.gson.annotations.SerializedName

data class Instances(
    @SerializedName("BoundingBox") val boundingBox: BoundingBox,
    @SerializedName("Confidence") val confidence: Double
)