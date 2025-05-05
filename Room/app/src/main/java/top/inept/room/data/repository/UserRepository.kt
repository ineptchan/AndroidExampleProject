package top.inept.room.data.repository

import kotlinx.coroutines.flow.Flow
import top.inept.room.data.entity.User

interface UserRepository : BaseRepository<User> {
    fun getUsers(): Flow<List<User>>
    fun getUserById(id: Int): Flow<User?>
}