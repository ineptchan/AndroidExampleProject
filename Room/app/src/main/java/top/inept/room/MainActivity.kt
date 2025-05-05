package top.inept.room

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import top.inept.room.ui.theme.RoomTheme
import top.inept.room.ui.user.UserHomeScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RoomTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // 最好配合Jetpack Compose Navigation使用
                    UserHomeScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
