package top.inept.voyager.voyager

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import top.inept.voyager.R
import top.inept.voyager.feature.settings.ui.SettingsAccountScreen
import top.inept.voyager.feature.settings.ui.SettingsAppsScreen
import top.inept.voyager.feature.settings.ui.SettingsHomeScreen
import top.inept.voyager.feature.settings.ui.SettingsNetworkScreen


object SettingsScreens {
    val screens = listOf(Account, Network, Apps)

    object Home : MyScreen {
        override val name: Int = R.string.home
        override val icon: ImageVector = Icons.Default.Home

        @Composable
        override fun Content() = SettingsHomeScreen()
    }

    object Account : MyScreen {
        override val name: Int = R.string.account
        override val icon: ImageVector = Icons.Default.AccountCircle

        @Composable
        override fun Content() = SettingsAccountScreen()
    }

    object Network : MyScreen {
        override val name: Int = R.string.network
        override val icon: ImageVector = Icons.Default.Wifi

        @Composable
        override fun Content() = SettingsNetworkScreen()
    }

    object Apps : MyScreen {
        override val name: Int = R.string.apps
        override val icon: ImageVector = Icons.Default.Apps

        @Composable
        override fun Content() = SettingsAppsScreen()
    }
}