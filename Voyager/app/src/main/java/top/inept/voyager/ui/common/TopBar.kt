package top.inept.voyager.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    title: String,
    back: (() -> Unit)? = null,
) {
    Column(modifier = modifier) {
        Box {
            if (back != null) {
                Icon(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .clickable { back() },
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = "Back"
                )
            }
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )
        }
        HorizontalDivider()
    }
}