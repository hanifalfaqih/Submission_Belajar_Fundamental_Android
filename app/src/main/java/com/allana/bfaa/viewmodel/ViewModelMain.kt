package com.allana.bfaa.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.allana.bfaa.model.ResponseSearchUser
import com.allana.bfaa.repository.RepositoryUser

class ViewModelMain: ViewModel() {

    /**
     * init repository
     */
    private val repositoryMain = RepositoryUser()

    /**
     * handle repository main with mutable live data
     */
    var isError = MutableLiveData<Throwable>()
    var responseSearch = MutableLiveData<ResponseSearchUser>()

    /**
     * handle progress bar
     */
    var isLoading = MutableLiveData<Boolean>()

    fun getSearchUser(username: String) {
        isLoading.value = true
        repositoryMain.getSearchUser(username, {
            responseSearch.value = it
            isLoading.value = false
        }, {
            isError.value = it
            isLoading.value = false
        })
    }

    fun getDisposableClear() {
        repositoryMain.onDetach()
    }
}