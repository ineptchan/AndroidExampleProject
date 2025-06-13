package top.inept.voyager.feature.user.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator

@Composable
fun UserScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val bottomSheetNavigator = LocalBottomSheetNavigator.current

        Column {
            Text("User")
            Button(onClick = {
                bottomSheetNavigator.show(UserInfo)
            }) { Text("ShowBottomSheet") }
        }
    }
}