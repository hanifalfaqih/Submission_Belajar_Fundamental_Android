package com.allana.consumeruserapp.helper

import android.database.Cursor
import com.allana.consumeruserapp.model.UserFavorite

object MappingHelper {
    fun Cursor.userFavoriteList(): ArrayList<UserFavorite> {
        val userFavoriteList = ArrayList<UserFavorite>()

        apply {
            while (moveToNext()) {
                userFavoriteList.add(
                    userFavorite()
                )
            }
        }
        return userFavoriteList
    }

    private fun Cursor.userFavorite(): UserFavorite = UserFavorite(
        getString(getColumnIndexOrThrow("username")),
        getString(getColumnIndexOrThrow("name")),
        getString(getColumnIndexOrThrow("location")),
        getString(getColumnIndexOrThrow("company")),
        getString(getColumnIndexOrThrow("avatar_url"))
    )
}