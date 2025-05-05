# Room

`create:2025-05-05`

`Room`,`Hilt`,`Jetpack Compose Navigation`配合使用,采用mvvm设计模式

## 1.Room

Room 是 Google 推出的 Android 持久化解决方案，是对原生 SQLite
的一个抽象层。它通过注解处理器（APT）在编译期生成类型安全、易维护的数据库访问代码，帮助开发者避免手写繁琐的
SQL 语句和Cursor 操作，同时兼容 RxJava、LiveData、Kotlin Coroutines/Flow 等响应式数据流。

在编译阶段，Room 的 APT 扫描项目中所有带有 `@Entity`、`@Dao`、`@Database`
等注解的类，生成实现数据库操作的辅助类（`RoomDatabase_Impl`、`XXX_Dao_Impl`）

## 2.添加Room配置依赖

这里推荐使用ksp而不是kapt

`Room\gradle\libs.versions.toml`
你需要注意的是ksp版本要与kotlin一致

```toml
[versions]
kotlin = "2.0.0"
ksp = "2.0.0-1.0.22"
room = "2.6.0"
hilt = "2.51.1"
hiltNavigationCompose = "1.2.0"

[libraries]
room-runtime = { group = "androidx.room", name = "room-runtime", version.ref = "room" }
room-compiler = { group = "androidx.room", name = "room-compiler", version.ref = "room" }
room-ktx = { group = "androidx.room", name = "room-ktx", version.ref = "room" }
hilt-android = { group = "com.google.dagger", name = "hilt-android", version.ref = "hilt" }
hilt-android-compiler = { group = "com.google.dagger", name = "hilt-android-compiler", version.ref = "hilt" }
hilt-navigation-compose = { group = "androidx.hilt", name = "hilt-navigation-compose", version.ref = "hiltNavigationCompose" }

[plugins]
ksp-plugin = { id = "com.google.devtools.ksp", version.ref = "ksp" }
hilt = { id = "com.google.dagger.hilt.android", version.ref = "hilt" }
```

---

`Room\build.gradle.kts`

```kotlin
plugins {
    alias(libs.plugins.ksp.plugin) apply false
    alias(libs.plugins.hilt) apply false
}
```

---

`Room\app\build.gradle.kts`

```kotlin
plugins {
    alias(libs.plugins.ksp.plugin)
    alias(libs.plugins.hilt)
}

dependencies {
    //Room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    //Hilt
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
    ksp(libs.hilt.android.compiler)
}
```

## 3.编写`Entity`实体类

`User`

```kotlin
package top.inept.room.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val username: String,
    val password: String,
    val level: Int,
)
```

- `tableName`表名
- `autoGenerate`自动生成id

## 4.编写`Dao`数据访问对象

`BaseDao` 其他dao继承会方便些

```kotlin
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
```

- 添加了`suspend`只能单独的线程上运行,因为Room 不允许在主线程上访问数据库,
- `onConflict = OnConflictStrategy.IGNORE`,遇到相同key改怎么处理,这里的`IGNORE`忽略此条插入

---

`UserDao`

```kotlin
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
```

- `Query`操作不需要`suspend`

## 5.编写`Database`数据库类

`MyDatabase`

```kotlin
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
```

## 6.编写`Repository`

`BaseRepository`

```kotlin
package top.inept.room.data.repository

interface BaseRepository<T> {
    suspend fun insert(obj: T)
    suspend fun insert(vararg obj: T)
    suspend fun delete(obj: T)
    suspend fun update(obj: T)
}
```

---

`UserRepository`

```kotlin
package top.inept.room.data.repository

import kotlinx.coroutines.flow.Flow
import top.inept.room.data.entity.User

interface UserRepository : BaseRepository<User> {
    fun getUsers(): Flow<List<User>>
    fun getUserById(id: Int): Flow<User?>
}
```

---

`OfflineBaseRepository`

```kotlin
package top.inept.room.data.repository.impl

import top.inept.room.data.dao.BaseDao
import top.inept.room.data.repository.BaseRepository

open class OfflineBaseRepository<T>(private val baseDao: BaseDao<T>) : BaseRepository<T> {
    override suspend fun insert(obj: T) = baseDao.insert(obj)

    override suspend fun insert(vararg obj: T) = baseDao.insert(*obj)

    override suspend fun delete(obj: T) = baseDao.delete(obj)

    override suspend fun update(obj: T) = baseDao.update(obj)
}
```

---

`OfflineUserRepository`

```kotlin
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
```

## 7.配置`MyApplication`与`MainActivity`

`MyApplication`

```kotlin
package top.inept.room

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application()
```

`AndroidManifest.xml`

```xml

<application android:name=".MyApplication" />
```

---

`MainActivity`

```kotlin
package top.inept.room

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    //...
}
```

## 8.编写HiltModule

`DatabaseModule`

```kotlin
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
```

## 9.编写`Jetpack Compose Navigation`相关


## 9.编写`Viewmodel`

```kotlin
package top.inept.room.ui.user

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import top.inept.room.data.entity.User
import top.inept.room.data.repository.UserRepository
import javax.inject.Inject

@HiltViewModel
class UserHomeViewmodel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel() {
    var userHomeUiState by mutableStateOf(UserHomeUiState())
        private set

    init {
        viewModelScope.launch {
            userRepository.getUsers().collect { users ->
                userHomeUiState = userHomeUiState.copy(users = users)
            }
        }
    }

    //这里先图方便了写到一个页面里,实际不是这样的
    suspend fun saveUser() {
        viewModelScope.launch {
            userRepository.insert(User(
                username =  randomString(4),
                password = randomString(16),
                level = 10
            ))
        }
    }

    private fun randomString(length: Int): String {
        val chars = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        return (1..length)
            .map { chars.random() }
            .joinToString("")
    }
}

data class UserHomeUiState(val users: List<User> = listOf())
```

## 10.编写`UserHomeScreen`

```kotlin
package top.inept.room.ui.user

//...

@Composable
fun UserHomeScreen(
    modifier: Modifier = Modifier,
    userViewmodel: UserHomeViewmodel = hiltViewModel(),
) {
    Surface(modifier = modifier) {
        val userList = userViewmodel.userHomeUiState.users
        val scope = rememberCoroutineScope()

        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        //这里先图方便了写到一个页面里,实际不是这样的
                        scope.launch {
                            userViewmodel.saveUser()
                        }
                    },
                    elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "添加"
                    )
                }
            },
            floatingActionButtonPosition = FabPosition.End,
        ) { innerPadding ->
            UserList(modifier = Modifier.padding(innerPadding), userList)
        }
    }
}


@Composable
fun UserList(modifier: Modifier = Modifier, userList: List<User>) {
    LazyColumn(modifier = modifier) {
        items(userList) { user ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    UserInfoRow(value = stringResource(R.string.user_home_id, user.id.toString()))
                    UserInfoRow(value = stringResource(R.string.user_home_username, user.username))
                    UserInfoRow(value = stringResource(R.string.user_home_password, user.password))
                    UserInfoRow(value = stringResource(R.string.user_home_level, user.level.toString()))
                }
            }
        }

    }
}

@Composable
fun UserInfoRow(value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}
```