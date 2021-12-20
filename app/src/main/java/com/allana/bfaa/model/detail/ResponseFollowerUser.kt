package com.allana.bfaa.model.detail

import com.google.gson.annotations.SerializedName

data class ResponseFollowerUser(
	@field:SerializedName("login")
	val login: String? = null,

	@field:SerializedName("avatar_url")
	val avatarUrl: String? = null,
)
