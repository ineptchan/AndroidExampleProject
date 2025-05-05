package top.inept.room.data.repository.impl

import top.inept.room.data.dao.BaseDao
import top.inept.room.data.repository.BaseRepository

open class OfflineBaseRepository<T>(private val baseDao: BaseDao<T>) : BaseRepository<T> {
    override suspend fun insert(obj: T) = baseDao.insert(obj)

    override suspend fun insert(vararg obj: T) = baseDao.insert(*obj)

    override suspend fun delete(obj: T) = baseDao.delete(obj)

    override suspend fun update(obj: T) = baseDao.update(obj)
}