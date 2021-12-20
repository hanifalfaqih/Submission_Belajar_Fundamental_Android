package com.allana.bfaa.viewmodel.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.allana.bfaa.model.detail.ResponseFollowingUser
import com.allana.bfaa.repository.RepositoryUser

class ViewModelFollowing: ViewModel() {
    /**
     * init repository
     */
    private val repositoryMain = RepositoryUser()

    /**
     * handle repository main with mutable live data
     */
    var isError = MutableLiveData<Throwable>()
    var responseFollowingUser = MutableLiveData<List<ResponseFollowingUser>>()

    fun getFollowingUser(username: String) {
        repositoryMain.getFollowingUser(username, {
            responseFollowingUser.value = it
        }, {
            isError.value = it
        })
    }
}