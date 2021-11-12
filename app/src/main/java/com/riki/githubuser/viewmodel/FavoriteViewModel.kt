package com.riki.githubuser.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.riki.githubuser.data.GithubRepository
import com.riki.githubuser.data.local.UserEntity

class FavoriteViewModel(application: Application) : ViewModel() {
    private val mGithubRepository: GithubRepository = GithubRepository(application)

    fun getListFavoriteUser(): LiveData<List<UserEntity>> = mGithubRepository.getListFavoriteUser()

    fun deleteFavoriteUser(user: UserEntity) = mGithubRepository.deleteFavoriteUser(user)

}