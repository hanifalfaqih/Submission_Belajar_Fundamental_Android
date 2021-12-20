package com.allana.bfaa.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.allana.bfaa.database.UserDatabase
import com.allana.bfaa.database.UserFavorite
import com.allana.bfaa.repository.RepositoryUserDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ViewModelUserDao(app: Application) : AndroidViewModel(app) {

    private val repositoryUserDao: RepositoryUserDao

    init {
        val userDao = UserDatabase.getInstance(app).userFavDao()
        repositoryUserDao = RepositoryUserDao(userDao)
    }

    fun getAllUser(): LiveData<List<UserFavorite>> {
        return repositoryUserDao.getAllUser()
    }

    fun getUserByUsername(username: String): LiveData<List<UserFavorite>> {
        return repositoryUserDao.getUserByUsername(username)
    }

    fun insertUser(user: UserFavorite) {
        viewModelScope.launch(Dispatchers.Main) {
            repositoryUserDao.insertUser(user)
        }
    }

    fun deleteUser(user: UserFavorite) {
        viewModelScope.launch(Dispatchers.Main) {
            repositoryUserDao.deleteUser(user)
        }
    }
}