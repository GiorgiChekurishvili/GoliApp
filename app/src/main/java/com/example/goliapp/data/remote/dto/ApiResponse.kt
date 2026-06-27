package com.example.goliapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ApiResponse<T>(
    @SerializedName("get") val get: String? = null,
    @SerializedName("parameters") val parameters: Map<String, String>? = null,
    @SerializedName("errors") val errors: List<String>? = null,
    @SerializedName("results") val results: Int = 0,
    @SerializedName("paging") val paging: PagingDto? = null,
    @SerializedName("response") val response: List<T> = emptyList()
)

data class PagingDto(
    @SerializedName("current") val current: Int = 1,
    @SerializedName("total") val total: Int = 1
)
