package com.allana.bfaa.network

import com.allana.bfaa.BuildConfig
import com.allana.bfaa.model.ResponseSearchUser
import com.allana.bfaa.model.detail.ResponseDetailUser
import com.allana.bfaa.model.detail.ResponseFollowerUser
import com.allana.bfaa.model.detail.ResponseFollowingUser
import de.hdodenhof.circleimageview.BuildConfig
import io.reactivex.rxjava3.core.Flowable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {

    // getSearchUser
    @GET("/search/users?")
    @Headers("Authorization: token ${BuildConfig.GITHUB_TOKEN}")
    fun getSearchUser(
        @Query("q") q : String
    ): Flowable<ResponseSearchUser>

    // getUserDetail
    @GET("/users/{username}")
    @Headers("Authorization: token ${BuildConfig.GITHUB_TOKEN}")
    fun getUserDetail(
        @Path("username") username: String
    ): Flowable<ResponseDetailUser>

    // getListFollower
    @GET("/users/{username}/followers")
    @Headers("Authorization: token ${BuildConfig.GITHUB_TOKEN}")
    fun getListFollower(
        @Path("username") username: String
    ): Flowable<List<ResponseFollowerUser>>

    // getListFollowing
    @GET("/users/{username}/following")
    @Headers("Authorization: token ${BuildConfig.GITHUB_TOKEN}")
    fun getListFollowing(
        @Path("username") username: String
    ): Flowable<List<ResponseFollowingUser>>
}