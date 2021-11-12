package com.riki.githubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.riki.githubuser.api.ApiConfig
import com.riki.githubuser.model.*
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel : ViewModel() {

    companion object {
        var TAG = "main_view_model"
    }

    private val _listUsers = MutableLiveData<List<UserModel>>()
    val listUsers: LiveData<List<UserModel>> = _listUsers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isLoadingFragment = MutableLiveData<Boolean>()
    val isLoadingFragment: LiveData<Boolean> = _isLoadingFragment


    init {
        getListUsers()
    }

    private fun getListUsers() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getListUsers()
        client.enqueue(object : Callback<List<UserModel>> {
            override fun onResponse(
                call: Call<List<UserModel>>,
                response: Response<List<UserModel>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listUsers.value = response.body()
                }
            }

            override fun onFailure(call: Call<List<UserModel>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun searchUser(q: String): LiveData<SearchModel> {
        _isLoading.value = true
        val searchResponse = MutableLiveData<SearchModel>()
        val client = ApiConfig.getApiService().searchUser(q)
        client.enqueue(object : Callback<SearchModel> {
            override fun onResponse(
                call: Call<SearchModel>,
                response: Response<SearchModel>
            ) {
                try {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        searchResponse.value = response.body()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }


            }

            override fun onFailure(call: Call<SearchModel>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })

        return searchResponse
    }

    fun getFollower(login: String): LiveData<List<FollowerModel>> {
        _isLoadingFragment.value = true
        val listFollower = MutableLiveData<List<FollowerModel>>()
        val client = ApiConfig.getApiService().getFollower(login)
        client.enqueue(object : Callback<List<FollowerModel>> {
            override fun onResponse(
                call: Call<List<FollowerModel>>,
                response: Response<List<FollowerModel>>
            ) {
                try {
                    _isLoadingFragment.value = false
                    if (response.isSuccessful) {
                        listFollower.value = response.body()
                    } else {
                        Log.e(TAG, "onFailure: test")
                    }
                } catch (e: JSONException) {
                    Log.e("json", "unexpected JSON exception", e)

                }

            }

            override fun onFailure(call: Call<List<FollowerModel>>, t: Throwable) {
                _isLoadingFragment.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })

        return listFollower
    }

    fun getFollowing(login: String): LiveData<List<FollowingModel>> {
        _isLoadingFragment.value = true
        val listFollowing = MutableLiveData<List<FollowingModel>>()
        val client = ApiConfig.getApiService().getFollowing(login)
        client.enqueue(object : Callback<List<FollowingModel>> {
            override fun onResponse(
                call: Call<List<FollowingModel>>,
                response: Response<List<FollowingModel>>
            ) {
                _isLoadingFragment.value = false
                if (response.isSuccessful) {
                    listFollowing.value = response.body()
                }
            }

            override fun onFailure(call: Call<List<FollowingModel>>, t: Throwable) {
                _isLoadingFragment.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
        return listFollowing
    }

}