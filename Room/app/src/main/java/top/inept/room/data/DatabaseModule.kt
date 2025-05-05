package top.inept.room.data

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import top.inept.room.data.dao.UserDao
import top.inept.room.data.repository.UserRepository
import top.inept.room.data.repository.impl.OfflineUserRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext appContext: Context): MyDatabase {
        val callback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
            }

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
            }

            override fun onDestructiveMigration(db: SupportSQLiteDatabase) {
                super.onDestructiveMigration(db)
            }
        }

        return Room.databaseBuilder(
            appContext,
            MyDatabase::class.java,
            "database.db"
        ).addCallback(callback).build()
    }


    @Provides
    fun providesUserDao(database: MyDatabase): UserDao = database.userDao()

    @Provides
    @Singleton
    fun providesUserRepository(userDao: UserDao): UserRepository = OfflineUserRepository(userDao)
}