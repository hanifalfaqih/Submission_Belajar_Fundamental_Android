package com.allana.consumeruserapp

import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.allana.consumeruserapp.adapter.MainAdapter
import com.allana.consumeruserapp.helper.MappingHelper.userFavoriteList
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        /**
         * The authority of this content provider
         */
        private const val AUTHORITY = "com.allana.bfaa.contentprovider"
        private const val SCHEME = "content"
        private const val TABLE_NAME = "user_favorite"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = getString(R.string.title)
        getUserFromContentProvider()
    }

    private fun getUserFromContentProvider() {
        /**
         * create URI
         */
        val contentUri = Uri.Builder()
            .scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build()

        /**
         * cursor query to get data from content provider use content resolver
         */
        val cursor = contentResolver.query(
            contentUri,
            null,
            null,
            null,
            null
        )

        /**
         * check cursor if null
         */
        if (cursor != null) {
            showListUser(cursor)
        }
    }

    /**
     * show data from content resolver use recycler view
     */
    private fun showListUser(cursor: Cursor?) {
        val adapter = cursor?.let {
            MainAdapter(it.userFavoriteList()) }
        rvFavUserContentResolver.layoutManager = LinearLayoutManager(this)
        rvFavUserContentResolver.adapter = adapter

        cursor?.let {
            MainAdapter(cursor.userFavoriteList())
        }
    }
}
