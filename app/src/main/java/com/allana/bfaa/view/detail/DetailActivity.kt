package com.allana.bfaa.view.detail

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.allana.bfaa.R
import com.allana.bfaa.adapter.SectionsPagerAdapter
import com.allana.bfaa.database.UserFavorite
import com.allana.bfaa.model.detail.ResponseDetailUser
import com.allana.bfaa.view.FavoriteUserActivity
import com.allana.bfaa.viewmodel.ViewModelUserDao
import com.allana.bfaa.viewmodel.detail.ViewModelDetail
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {


    private lateinit var viewModelDetail: ViewModelDetail
    private lateinit var viewModelUserDao: ViewModelUserDao
    private lateinit var username: String
    private lateinit var userFav: UserFavorite
    private var isFavorite: Boolean = false

    companion object {
        const val EXTRA_USERNAME = "extra_username"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        /**
         * get username from intent
         */
        username = intent.getStringExtra(EXTRA_USERNAME) as String

        supportActionBar?.title = username
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        /**
         * init viewmodel
         */
        viewModelDetail = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(ViewModelDetail::class.java)
        viewModelUserDao = ViewModelProvider(this).get(ViewModelUserDao::class.java)

        /**
         * call getDetailUser
         */
        username.let {
            viewModelDetail.getDetailUser(it)
        }

        /**
         * setup tab layout for fragment following and follower
         */
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        sectionsPagerAdapter.username = username // todo 2
        view_pager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(view_pager)

        /**
         * function helper
         */
        attachObserverDetail()
        attachObserverUtils()
        tabsSelected()

        /**
         * setup for favorite button
         */
        setStatusFavorite(isFavorite)
    }

    private fun attachObserverDetail() {
        viewModelDetail.responseDetail.observe(this, {
            showDetailUser(it)
            addUserToFavorite(it)
        })
        viewModelUserDao.getUserByUsername(username).observe(this, {
            handleUserFavorite(it)
        })
    }

    private fun attachObserverUtils() {
        viewModelDetail.isError.observe(this, {
            showError(it) })
        viewModelDetail.isLoading.observe(this, {
            showLoading(it) })
    }

    private fun showError(it: Throwable) {
        Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(it: Boolean) {
        if (it) {
            progressBar.visibility = View.VISIBLE
            img_avatar.visibility = View.INVISIBLE
            tv_name.visibility = View.INVISIBLE
            tv_bio.visibility = View.INVISIBLE
            tv_location.visibility = View.INVISIBLE
            tv_company.visibility = View.INVISIBLE

        } else {
            progressBar.visibility = View.INVISIBLE
            img_avatar.visibility = View.VISIBLE
            tv_name.visibility = View.VISIBLE
            tv_bio.visibility = View.VISIBLE
            tv_location.visibility = View.VISIBLE
            tv_company.visibility = View.VISIBLE
        }
    }

    private fun showDetailUser(detailUser: ResponseDetailUser) {
        tv_name.text = detailUser.name
        tv_bio.text = detailUser.bio ?: getString(R.string.default_text_bio)
        tv_location.text = detailUser.location ?: getString(R.string.default_text_location)
        tv_company.text = detailUser.company ?: getString(R.string.default_text_company)
        Glide.with(this)
            .load(detailUser.avatarUrl)
            .into(img_avatar)
    }

    private fun handleUserFavorite(userFavList: List<UserFavorite>) {
        if (!userFavList.isNullOrEmpty()) {
            isFavorite = true
            setStatusFavorite(isFavorite)
        } else {
            isFavorite = false
            setStatusFavorite(isFavorite)
        }
    }

    private fun addUserToFavorite(detailUser: ResponseDetailUser) {
        favButton.setOnClickListener {
            isFavorite = !isFavorite
            userFav = detailUser.login?.let { UserFavorite(it, detailUser.name, detailUser.location, detailUser.company, detailUser.avatarUrl) }!!
            if (isFavorite) {
                userFav.let { viewModelUserDao.insertUser(it) }
                Toast.makeText(this, getString(R.string.insert_user_success), Toast.LENGTH_SHORT).show()
            } else {
                userFav.let { viewModelUserDao.deleteUser(it) }
                Toast.makeText(this, getString(R.string.delete_user_success), Toast.LENGTH_SHORT).show()
            }
            setStatusFavorite(isFavorite)
        }
    }

    private fun setStatusFavorite(favorite: Boolean) {
        if (favorite) {
            val icon = R.drawable.ic_baseline_favorite
            favButton.setImageResource(icon)
        } else {
            val icon = R.drawable.ic_baseline_favorite_border
            favButton.setImageResource(icon)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModelDetail.getDisposableClear()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun tabsSelected() {
        tabs.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite_user_detail -> {
                val intentToFavorite = Intent(this@DetailActivity, FavoriteUserActivity::class.java)
                startActivity(intentToFavorite)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}