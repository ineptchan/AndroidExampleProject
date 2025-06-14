package top.inept.voyager.feature.settings.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import top.inept.voyager.voyager.SettingsScreens

@Composable
fun SettingsScreen() {
    Navigator(SettingsScreens.Home) { navigator ->
        CurrentScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SettingsScreenPreview() {
    SettingsScreen()
}