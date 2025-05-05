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