package com.riki.githubuser.api

import com.riki.githubuser.model.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("users")
    fun getListUsers(): Call<List<UserModel>>

    @GET("search/users")
    fun searchUser(@Query("q") q: String): Call<SearchModel>

    @GET("users/{login}")
    fun getDetailUsers(@Path("login") login: String): Call<DetailUserModel>

    @GET("users/{login}/followers")
    fun getFollower(@Path("login") login: String): Call<List<FollowerModel>>

    @GET("users/{login}/following")
    fun getFollowing(@Path("login") login: String): Call<List<FollowingModel>>

}