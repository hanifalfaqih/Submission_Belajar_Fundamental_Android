package com.allana.bfaa.contentprovider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.allana.bfaa.R
import com.allana.bfaa.database.UserDatabase
import com.allana.bfaa.database.UserFavoriteDao

class UserContentProvider : ContentProvider() {

    companion object {
        /**
         * The authority of this content provider
         */
        private const val AUTHORITY = "com.allana.bfaa.contentprovider"
        private const val TABLE_NAME = "user_favorite"
        private const val FAVORITE = 1
        private const val SCHEME = "content"

        internal val CONTENT_URI: Uri = Uri.Builder()
            .scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build()

        /**
         * check URI
         */
        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        /**
         * late init userFavoriteDao
         */
        private lateinit var userFavoriteDao: UserFavoriteDao

        init {
            /**
             * content://com.allana.bfaa.contentprovider/user_favorite
             */
            sUriMatcher.addURI(AUTHORITY, TABLE_NAME, FAVORITE)
        }
    }

    override fun onCreate(): Boolean {
        context?.let {
            userFavoriteDao = UserDatabase.getInstance(it).userFavDao()
        }
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        return when (sUriMatcher.match(uri)) {
            FAVORITE -> userFavoriteDao.getAllUserContentProvider()
            else -> null
        }
    }

    override fun getType(uri: Uri): String? {
        throw UnsupportedOperationException(context?.getString(R.string.throw_content_provider))
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        throw UnsupportedOperationException(context?.getString(R.string.throw_content_provider))
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        throw UnsupportedOperationException(context?.getString(R.string.throw_content_provider))
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        throw UnsupportedOperationException(context?.getString(R.string.throw_content_provider))
    }
}
