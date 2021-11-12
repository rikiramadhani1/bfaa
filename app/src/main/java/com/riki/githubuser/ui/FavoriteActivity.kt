package com.riki.githubuser.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.riki.githubuser.R
import com.riki.githubuser.data.local.UserEntity
import com.riki.githubuser.databinding.ActivityFavoriteBinding
import com.riki.githubuser.adapter.FavoriteAdapter
import com.riki.githubuser.viewmodel.FavoriteViewModel
import com.riki.githubuser.viewmodel.ViewModelFactory

class FavoriteActivity : AppCompatActivity() {

    private lateinit var favoriteBinding: ActivityFavoriteBinding
    private lateinit var favoriteViewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favoriteBinding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(favoriteBinding.root)

        setupToolbar()

        supportActionBar?.title = "Favorite"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val factory = ViewModelFactory.getInstance(this@FavoriteActivity.application)
        favoriteViewModel = ViewModelProvider(this, factory).get(FavoriteViewModel::class.java)

        favoriteViewModel.getListFavoriteUser().observe(this, {
            setupAdapter(it)
        })
    }

    private fun setupToolbar() {
        favoriteBinding.toolbar.setTitleTextColor(ContextCompat.getColor(this, android.R.color.white))
        setSupportActionBar(favoriteBinding.toolbar)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_setting, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.setting -> {
                startActivity(Intent(this, SettingActivity::class.java))
                true
            }
            else -> true
        }
    }

    private fun setupAdapter(listUser: List<UserEntity>) {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val adapter = FavoriteAdapter(listUser)
        adapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserEntity) {
                favoriteViewModel.deleteFavoriteUser(data)
            }
        })

        favoriteBinding.rvFavorite.layoutManager = layoutManager
        favoriteBinding.rvFavorite.adapter = adapter
    }

}