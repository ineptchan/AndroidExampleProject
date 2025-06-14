package top.inept.voyager.voyager

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import top.inept.voyager.R
import top.inept.voyager.feature.home.ui.HomeScreen
import top.inept.voyager.feature.settings.ui.SettingsScreen
import top.inept.voyager.feature.user.ui.UserScreen
import top.inept.voyager.uilts.BuildTabOptions

object MainTabs {
    val tabs = listOf(HomeTab, UserTab, SettingsTab)

    object HomeTab : Tab {
        override val options: TabOptions
            @Composable
            get() = BuildTabOptions.buildTabOptions(0u, R.string.home, Icons.Default.Home)

        @Composable
        override fun Content() = HomeScreen()
    }

    object UserTab : Tab {
        override val options: TabOptions
            @Composable
            get() = BuildTabOptions.buildTabOptions(0u, R.string.home, Icons.Default.AccountCircle)

        @Composable
        override fun Content() = UserScreen()
    }

    object SettingsTab : Tab {
        override val options: TabOptions
            @Composable
            get() = BuildTabOptions.buildTabOptions(
                0u,
                R.string.settings,
                Icons.Default.Settings
            )

        @Composable
        override fun Content() = SettingsScreen()
    }
}