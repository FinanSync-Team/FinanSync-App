package com.example.alp.core.source.remote.response

import com.google.gson.annotations.SerializedName

data class BaseApiResponse<D, E>(
    @SerializedName("message") val message: String,

    @SerializedName("data") val data:D? = null,

    @SerializedName("errors") val errors: E? = null
)