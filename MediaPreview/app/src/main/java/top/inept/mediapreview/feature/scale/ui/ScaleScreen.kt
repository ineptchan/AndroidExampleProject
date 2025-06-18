package top.inept.mediapreview.feature.scale.ui

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import top.inept.mediapreview.voyager.ScaleScreen

@Composable
fun ScaleScreen() {
    Navigator(ScaleScreen.Home){
        CurrentScreen()
    }
}