package com.allana.bfaa.viewmodel.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.allana.bfaa.model.detail.ResponseFollowerUser
import com.allana.bfaa.repository.RepositoryUser

class ViewModelFollower: ViewModel() {
    /**
     * init repository
     */
    private val repositoryMain = RepositoryUser()

    /**
     * handle repository main with mutable live data
     */
    var isError = MutableLiveData<Throwable>()
    var responseFollowerUser = MutableLiveData<List<ResponseFollowerUser>>()

    fun getFollowerUser(username: String) {
        repositoryMain.getFollowerUser(username, {
            responseFollowerUser.value = it
        }, {
            isError.value = it
        })
    }
}