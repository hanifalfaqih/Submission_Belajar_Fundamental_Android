package com.allana.bfaa.viewmodel.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.allana.bfaa.model.detail.ResponseDetailUser
import com.allana.bfaa.repository.RepositoryUser

class ViewModelDetail: ViewModel() {

    /**
     * init repository
     */
    private val repositoryMain = RepositoryUser()

    /**
     * handle repository main with mutable live data
     */
    var isError = MutableLiveData<Throwable>()
    var responseDetail = MutableLiveData<ResponseDetailUser>()

    /**
     * handle progress bar
     */
    var isLoading = MutableLiveData<Boolean>()

    fun getDetailUser(username: String) {
        isLoading.value = true
        repositoryMain.getDetailUser(username, {
            responseDetail.value = it
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