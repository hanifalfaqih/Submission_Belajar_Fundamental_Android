package com.allana.bfaa.database

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "user_favorite")
data class UserFavorite (

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "username")
    val username: String,

    @ColumnInfo(name = "name")
    val name: String?,

    @ColumnInfo(name = "location")
    val location: String?,

    @ColumnInfo(name = "company")
    val company: String?,

    @ColumnInfo(name = "avatar_url")
    val avatar_url: String?

    ) : Parcelable