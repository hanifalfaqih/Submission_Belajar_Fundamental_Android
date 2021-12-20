package com.allana.consumeruserapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserFavorite(
    val username: String?,
    val name: String?,
    val location: String?,
    val company: String?,
    val avatar_url: String
): Parcelable
