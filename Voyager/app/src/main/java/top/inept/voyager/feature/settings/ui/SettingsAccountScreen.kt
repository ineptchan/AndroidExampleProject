package top.inept.voyager.feature.settings.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import top.inept.voyager.ui.common.TopBar
import top.inept.voyager.voyager.SettingsScreens

@Composable
fun SettingsAccountScreen() {
    val navigator = LocalNavigator.currentOrThrow

    Column {
        TopBar(
            title = "SettingsAccount",
            back = { navigator.pop() }
        )

        //这里假装没网络提示到网络设置
        //导航路径也就是 根(SettingsTab) -> SettingsHome -> SettingsAccount -> SettingsNetwork
        //点返回也是逐级返回的
        Card(
            onClick = {
                navigator.push(SettingsScreens.Network)
            },
            modifier = Modifier.padding(8.dp)
        ) {
            Row(modifier = Modifier.padding(8.dp)) {
                Icon(imageVector = Icons.Default.ErrorOutline, contentDescription = null)
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "No network, click to network settings",
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}