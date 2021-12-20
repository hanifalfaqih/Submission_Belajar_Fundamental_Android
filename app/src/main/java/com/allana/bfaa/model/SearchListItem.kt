package com.allana.bfaa.model

import com.google.gson.annotations.SerializedName

data class SearchListItem(
    @field:SerializedName("login")
    val login: String? = null,

    @field:SerializedName("avatar_url")
    val avatarUrl: String? = null,
)