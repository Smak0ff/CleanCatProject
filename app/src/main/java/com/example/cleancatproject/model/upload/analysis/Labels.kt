package com.example.cleancatproject.model.upload.analysis

import com.google.gson.annotations.SerializedName

data class Labels(
    @SerializedName("Name") val name: String,
    @SerializedName("Confidence") val confidence: Double,
    @SerializedName("Instances") val instances: List<Instances>,
    @SerializedName("Parents") val parents: List<Parents>
)