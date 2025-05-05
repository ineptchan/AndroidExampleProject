package top.inept.room.data.repository.impl

import kotlinx.coroutines.flow.Flow
import top.inept.room.data.dao.UserDao
import top.inept.room.data.entity.User
import top.inept.room.data.repository.UserRepository
import javax.inject.Inject

class OfflineUserRepository @Inject constructor(private val userDao: UserDao) :
    OfflineBaseRepository<User>(userDao), UserRepository {
    override fun getUserById(id: Int): Flow<User?> = userDao.getUserById(id)

    override fun getUsers(): Flow<List<User>> = userDao.getUsers()
}