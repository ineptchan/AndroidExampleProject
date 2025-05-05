package top.inept.room.data

import androidx.room.Database
import androidx.room.RoomDatabase
import top.inept.room.data.dao.UserDao
import top.inept.room.data.entity.User

@Database(
    entities = [User::class],
    version = 1,
    exportSchema = true
)
abstract class MyDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}