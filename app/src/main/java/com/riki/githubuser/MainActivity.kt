package com.riki.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.riki.githubuser.adapter.UserAdapter
import com.riki.githubuser.databinding.ActivityMainBinding
import com.riki.githubuser.model.UserModel
import com.riki.githubuser.ui.FavoriteActivity
import com.riki.githubuser.ui.SettingActivity
import com.riki.githubuser.viewmodel.UserViewModel
import android.widget.Toast




class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var mainViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(UserViewModel::class.java)

        mainViewModel.listUsers.observe(this, { listUser ->
            setupListUser(listUser)
        })

        mainViewModel.isLoading.observe(this, {
            setLoading(it)
        })

    }

    private var back_pressed: Long = 0

    override fun onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) super.onBackPressed() else Toast.makeText(
            baseContext, "Press once again to exit!", Toast.LENGTH_SHORT
        ).show()
        back_pressed = System.currentTimeMillis()
    }

    private fun setLoading(state: Boolean) {
        if (state) {
            mainBinding.rvUser.visibility = View.GONE
            mainBinding.progressBar.visibility = View.VISIBLE
        } else {
            mainBinding.rvUser.visibility = View.VISIBLE
            mainBinding.progressBar.visibility = View.GONE
        }
    }

    private fun setupListUser(listUser: List<UserModel>) {

        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val adapter = UserAdapter(listUser)

        mainBinding.rvUser.setHasFixedSize(true)
        mainBinding.rvUser.layoutManager = linearLayoutManager
        mainBinding.rvUser.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                mainViewModel.searchUser(query).observe(this@MainActivity, { response ->
                    if (response.totalCount > 0) {
                        setupListUser(response.items)
                    }
                })
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite -> {
                startActivity(Intent(this, FavoriteActivity::class.java))
            }
            R.id.setting -> {
                startActivity(Intent(this, SettingActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }
}