package top.inept.voyager.feature.settings.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import top.inept.voyager.ui.common.TopBar
import top.inept.voyager.voyager.SettingsScreens

@Composable
fun SettingsHomeScreen() {
    val navigator = LocalNavigator.currentOrThrow

    Column {
        TopBar(
            title = "SettingsHome"
        )

        LazyColumn {
            items(SettingsScreens.screens) { screen ->
                SettingsItem(
                    name = stringResource(screen.name),
                    icon = screen.icon,
                    onClick = {
                        navigator.push(screen)
                    }
                )
            }
        }
    }
}

@Composable
fun SettingsItem(name: String, icon: ImageVector, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.padding(8.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = name,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = name,
                style = MaterialTheme.typography.headlineLarge
            )
        }
    }
}