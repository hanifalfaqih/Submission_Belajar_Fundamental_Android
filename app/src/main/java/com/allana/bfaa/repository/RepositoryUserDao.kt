package com.allana.bfaa.repository

import androidx.lifecycle.LiveData
import com.allana.bfaa.database.UserFavorite
import com.allana.bfaa.database.UserFavoriteDao


class RepositoryUserDao(private val userDao: UserFavoriteDao) {

    fun getAllUser(): LiveData<List<UserFavorite>> {
        return userDao.getAllUser()
    }

    fun getUserByUsername(username: String): LiveData<List<UserFavorite>> {
        return userDao.getUserByUsername(username)
    }

    suspend fun insertUser(user: UserFavorite) {
        userDao.insertUser(user)
    }

    suspend fun deleteUser(user: UserFavorite) {
        userDao.deleteUser(user)
    }

}