package com.riki.githubuser.data.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface GithubDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: UserEntity)

    @Update
    fun update(user: UserEntity)

    @Delete
    fun delete(user: UserEntity)

    @Query("SELECT * FROM user WHERE id=:id")
    fun getFavoriteUser(id: Int): Boolean

    @Query("SELECT * from user ORDER BY id ASC")
    fun getListFavoriteUser(): LiveData<List<UserEntity>>
}