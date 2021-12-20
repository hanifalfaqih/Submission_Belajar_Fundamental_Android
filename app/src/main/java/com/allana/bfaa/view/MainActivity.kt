package com.allana.bfaa.view

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.allana.bfaa.R
import com.allana.bfaa.adapter.SearchUserAdapter
import com.allana.bfaa.model.SearchListItem
import com.allana.bfaa.setting.SettingsActivity
import com.allana.bfaa.view.detail.DetailActivity
import com.allana.bfaa.viewmodel.ViewModelMain
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModelMain: ViewModelMain

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModelMain = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(ViewModelMain::class.java)

        attachObserveUtils()
        attachObserveSearchList()
    }

    private fun attachObserveUtils() {
        viewModelMain.isError.observe(this, { showError(it) })
        viewModelMain.isLoading.observe(this, { showLoading(it) })
    }

    private fun attachObserveSearchList() {
        viewModelMain.responseSearch.observe(this, { it.items?.let { it1 -> showSearchList(it1) } })
    }

    private fun showLoading(it: Boolean?) {
        if (it == true) {
            progressBar.visibility = View.VISIBLE
            recyclerView.visibility = View.INVISIBLE
        } else {
            progressBar.visibility = View.INVISIBLE
            recyclerView.visibility = View.VISIBLE
        }
    }

    private fun showError(it: Throwable) {
        Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
    }

    private fun showSearchList(searchListItem: List<SearchListItem>) {
        val adapter = SearchUserAdapter(searchListItem)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        adapter.setOnItemClickCallback(object: SearchUserAdapter.OnItemClickUserList{
            override fun onItemClicked(listUser: SearchListItem) {
                showSelectedUser(listUser)
            }

        })
    }

    private fun showSelectedUser(listUser: SearchListItem?) {
        val intentToDetail = Intent(this@MainActivity, DetailActivity::class.java)
        intentToDetail.putExtra(DetailActivity.EXTRA_USERNAME, listUser?.login)
        startActivity(intentToDetail)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.app_bar_search)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    if (query.isNotEmpty()) {
                        viewModelMain.getSearchUser(query)
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite_user -> {
                val intentToFavorite = Intent(this@MainActivity, FavoriteUserActivity::class.java)
                startActivity(intentToFavorite)
            }
            R.id.change_language -> {
                val intentToLanguange = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(intentToLanguange)
            }
            R.id.alarm_manager -> {
                val intentToSettings = Intent(this@MainActivity, SettingsActivity::class.java)
                startActivity(intentToSettings)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModelMain.getDisposableClear()
    }

    override fun onResume() {
        super.onResume()
        attachObserveSearchList()
    }
}