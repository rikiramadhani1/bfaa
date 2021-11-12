package com.riki.githubuser.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.riki.githubuser.api.ApiConfig
import com.riki.githubuser.data.GithubRepository
import com.riki.githubuser.data.local.UserEntity
import com.riki.githubuser.model.DetailUserModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : ViewModel() {
    companion object {
        var TAG = "detail_view_model"
    }

    private val mGithubRepository: GithubRepository = GithubRepository(application)


    private val _userEntity = MutableLiveData<UserEntity>()
    private val userEntity: LiveData<UserEntity> = _userEntity

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getDetailUser(login: String): LiveData<DetailUserModel> {
        _isLoading.value = true
        val user = MutableLiveData<DetailUserModel>()
        val client = ApiConfig.getApiService().getDetailUsers(login)
        client.enqueue(object : Callback<DetailUserModel> {
            override fun onResponse(
                call: Call<DetailUserModel>,
                response: Response<DetailUserModel>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    user.value = response.body()
                    _userEntity.value = UserEntity(
                        id = response.body()?.id,
                        login = response.body()?.login,
                        repos_url = response.body()?.reposUrl,
                        avatar_url = response.body()?.avatarUrl,
                        company = response.body()?.company,
                        location = response.body()?.location,
                        name = response.body()?.name,
                        isFav = false
                    )
                }
            }

            override fun onFailure(call: Call<DetailUserModel>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })

        return user
    }

    fun setFavoriteUser() = userEntity.value?.let {
        mGithubRepository.getFavoriteUser(it) }
}