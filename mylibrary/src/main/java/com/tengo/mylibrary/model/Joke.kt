package com.tengo.mylibrary.model

import com.google.gson.annotations.SerializedName

data class Joke(
    @SerializedName("joke") val description: String,
    @SerializedName("id") val id: Int,
    @SerializedName("categories") val categories: List<String>
)