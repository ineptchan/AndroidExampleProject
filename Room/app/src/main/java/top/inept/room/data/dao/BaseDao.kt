package top.inept.room.data.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(obj: T)

    @Insert
    suspend fun insert(vararg obj: T)

    @Delete
    suspend fun delete(obj: T)

    @Update
    suspend fun update(obj: T)
}