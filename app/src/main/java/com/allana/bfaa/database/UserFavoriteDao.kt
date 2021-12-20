package com.allana.bfaa.database

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserFavoriteDao {

    @Query("SELECT * FROM user_favorite")
    fun getAllUser(): LiveData<List<UserFavorite>>

    @Query("SELECT * FROM user_favorite WHERE username = :username")
    fun getUserByUsername(username: String): LiveData<List<UserFavorite>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserFavorite)

    @Delete
    suspend fun deleteUser(user: UserFavorite)

    /**
     * content provider
     */
    @Query("SELECT * FROM user_favorite")
    fun getAllUserContentProvider(): Cursor
}