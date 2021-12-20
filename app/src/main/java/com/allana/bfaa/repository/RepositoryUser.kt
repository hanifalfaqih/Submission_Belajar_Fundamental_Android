package com.allana.bfaa.repository

import com.allana.bfaa.model.ResponseSearchUser
import com.allana.bfaa.model.detail.ResponseDetailUser
import com.allana.bfaa.model.detail.ResponseFollowerUser
import com.allana.bfaa.model.detail.ResponseFollowingUser
import com.allana.bfaa.network.UserNetwork
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers

class RepositoryUser {

    private val disposables: CompositeDisposable = CompositeDisposable()

    /**
     * get search user
     */
    fun getSearchUser(query: String, responseSearchUser: (ResponseSearchUser) -> Unit, error: (Throwable) -> Unit) {
        UserNetwork.getService().getSearchUser(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                responseSearchUser(it)
            }, {
                error(it)
            }).addTo(disposables)
    }

    /**
     * get detail user
     */
    fun getDetailUser(path: String, responseDetailUser: (ResponseDetailUser) -> Unit, error: (Throwable) -> Unit) {
        UserNetwork.getService().getUserDetail(path)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                responseDetailUser(it)
            }, {
                error(it)
            }).addTo(disposables)
    }

    /**
     * get following user
     */
    fun getFollowingUser(query: String, responseFollowingUser: (List<ResponseFollowingUser>) -> Unit, error: (Throwable) -> Unit) {
        UserNetwork.getService().getListFollowing(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                responseFollowingUser(it)
            }, {
                error(it)
            }).addTo(disposables)
    }

    /**
     * get follower user
     */
    fun getFollowerUser(query: String, responseFollowerUser: (List<ResponseFollowerUser>) -> Unit, error: (Throwable) -> Unit) {
        UserNetwork.getService().getListFollower(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                responseFollowerUser(it)
            }, {
                error(it)
            }).addTo(disposables)
    }

    fun onDetach() {
        disposables.clear()
    }

}