package com.riki.githubuser.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.riki.githubuser.R
import com.riki.githubuser.adapter.SectionsPagerAdapter
import com.riki.githubuser.data.local.UserEntity
import com.riki.githubuser.databinding.ActivityDetailUserBinding
import com.riki.githubuser.databinding.ContentDetailUserBinding
import com.riki.githubuser.model.DetailUserModel
import com.riki.githubuser.viewmodel.DetailViewModel
import com.riki.githubuser.viewmodel.ViewModelFactory


class DetailUserActivity : AppCompatActivity() {

    private lateinit var detailUserBinding: ActivityDetailUserBinding
    private lateinit var contentDetailUserBinding: ContentDetailUserBinding
    private lateinit var detailViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailUserBinding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(detailUserBinding.root)
        supportActionBar?.title = "Detail user"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        contentDetailUserBinding = detailUserBinding.detailContent

        val user = intent.getParcelableExtra<UserEntity>(EXTRA_USER) as UserEntity
        val factory = ViewModelFactory.getInstance(this@DetailUserActivity.application)

        detailViewModel =
            ViewModelProvider(
                this,
                factory
            ).get(DetailViewModel::class.java)

        detailViewModel.getDetailUser(user.login.toString()).observe(this, {
            setUser(it)
        })

        detailViewModel.isLoading.observe(this, {
            setLoading(it)
        })

        detailUserBinding.btnFav.setOnClickListener {
            var isFavorite = false
            isFavorite = !isFavorite
            detailViewModel.setFavoriteUser()
            setButtonFavorite(isFavorite)
        }

        user.login?.let { setupTab(it) }

    }

    private fun setButtonFavorite(favorite: Boolean) {

        if (favorite) {
            Toast.makeText(
                baseContext, "Added to favorite", Toast.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(
                baseContext, "Removed from favorite", Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun setupTab(login: String) {
        val sectionsPagerAdapter = SectionsPagerAdapter(this, login)
        contentDetailUserBinding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(
            contentDetailUserBinding.tabs,
            contentDetailUserBinding.viewPager
        ) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
    }

    private fun setUser(user: DetailUserModel) {
        with(contentDetailUserBinding) {
            tvName.text = user.name
            tvUsername.text = "@${user.login}"
            tvLocation.text = String.format(resources.getString(R.string.location), user.location)
            tvCompany.text = String.format(resources.getString(R.string.company), user.company)

            Glide.with(this@DetailUserActivity).load(user.avatarUrl).circleCrop().into(avatarUser)
        }

    }

    private fun setLoading(loading: Boolean) {
        if (loading) {
            detailUserBinding.progressBar.visibility = View.VISIBLE
            detailUserBinding.detailContent.layout.visibility = View.INVISIBLE
        } else {
            detailUserBinding.progressBar.visibility = View.GONE
            detailUserBinding.detailContent.layout.visibility = View.VISIBLE
        }
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_following,
            R.string.tab_follower
        )
        const val EXTRA_USER = "extra_user"
    }

}