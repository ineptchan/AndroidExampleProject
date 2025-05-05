package top.inept.room.ui.user

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import top.inept.room.R
import top.inept.room.data.entity.User

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

@Preview
@Composable
private fun UserListPreview() {
    val userList = listOf(
        User(1, "tom", "123456", 10),
        User(2, "jock", "123456", 10),
    )
    UserList(userList = userList)
}
