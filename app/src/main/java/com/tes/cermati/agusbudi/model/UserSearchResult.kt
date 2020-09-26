package com.tes.cermati.agusbudi.model
import com.google.gson.annotations.SerializedName


data class UserSearchResult(
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean?,
    @SerializedName("items")
    val items: List<UserItem?>,
    @SerializedName("total_count")
    val totalCount: Int?
)