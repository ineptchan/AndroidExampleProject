package top.inept.room.data.repository

interface BaseRepository<T> {
    suspend fun insert(obj: T)
    suspend fun insert(vararg obj: T)
    suspend fun delete(obj: T)
    suspend fun update(obj: T)
}