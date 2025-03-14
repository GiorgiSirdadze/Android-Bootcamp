package com.example.homeworks.data.local
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<UserEntity>)

    @Query("SELECT * FROM users ORDER BY id ASC")
    fun getPagedUsers(): PagingSource<Int, UserEntity>

    @Query("DELETE FROM users")
    suspend fun clearUsers()
}
