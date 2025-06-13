package top.inept.voyager.feature.user.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen

object UserInfo : Screen {
    @Composable
    override fun Content() {
        val configuration = LocalConfiguration.current

        Column(
            modifier = Modifier.height(configuration.screenHeightDp.dp / 2),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "This is UserInfo...",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

