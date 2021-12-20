package com.allana.bfaa.model.detail

import com.google.gson.annotations.SerializedName

data class ResponseDetailUser(

	@field:SerializedName("following_url")
	val followingUrl: String? = null,

	@field:SerializedName("bio")
	val bio: String? = null,

	@field:SerializedName("login")
	val login: String? = null,

	@field:SerializedName("avatar_url")
	val avatarUrl: String? = null,

	@field:SerializedName("company")
	val company: String? = null,

	@field:SerializedName("followers_url")
	val followersUrl: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("location")
	val location: String? = null,
)
