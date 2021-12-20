package com.allana.bfaa.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.allana.bfaa.R
import com.allana.bfaa.adapter.UserFavoriteAdapter
import com.allana.bfaa.database.UserFavorite
import com.allana.bfaa.view.detail.DetailActivity
import com.allana.bfaa.viewmodel.ViewModelUserDao
import kotlinx.android.synthetic.main.activity_favorite_user.*

class FavoriteUserActivity : AppCompatActivity() {

    private lateinit var viewModelUserDao: ViewModelUserDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_user)

        supportActionBar?.title = getString(R.string.favorite_users)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        viewModelUserDao = ViewModelProvider(this).get(ViewModelUserDao::class.java)

        viewModelUserDao.getAllUser()

        attachObserveFavoriteList()
    }

    private fun attachObserveFavoriteList() {
        viewModelUserDao.getAllUser().observe(this, {
            showListUserFavorite(it)
        })
    }

    private fun showListUserFavorite(userFavList: List<UserFavorite>) {
        val adapter = UserFavoriteAdapter(userFavList)
        rvFavoriteUser.layoutManager = LinearLayoutManager(this)
        rvFavoriteUser.adapter = adapter

        adapter.setOnItemClickCallback(object : UserFavoriteAdapter.OnItemClickUserFavorite {
            override fun onItemClicked(userFavList: UserFavorite) {
                showSelectedUserFavList(userFavList)
            }

        })
    }

    private fun showSelectedUserFavList(userFavList: UserFavorite) {
        val intentToDetail = Intent(this@FavoriteUserActivity, DetailActivity::class.java)
        intentToDetail.putExtra(DetailActivity.EXTRA_USERNAME, userFavList.username)
        startActivity(intentToDetail)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}