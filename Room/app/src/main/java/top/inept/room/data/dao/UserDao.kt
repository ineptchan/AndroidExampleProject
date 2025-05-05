package top.inept.room.data.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import top.inept.room.data.entity.User


@Dao
interface UserDao : BaseDao<User> {
    @Query("select id,username,password,level from users")
    fun getUsers(): Flow<List<User>>

    @Query("select id,username,password,level from users where id=:id")
    fun getUserById(id: Int): Flow<User?>
}