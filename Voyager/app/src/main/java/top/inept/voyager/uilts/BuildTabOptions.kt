package top.inept.voyager.uilts

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import cafe.adriel.voyager.navigator.tab.TabOptions

object BuildTabOptions {
    @Composable
     fun buildTabOptions(
        index: UShort,
        @StringRes titleResId: Int,
        icon: ImageVector,
    ): TabOptions {
        val title = stringResource(titleResId)
        val painter = rememberVectorPainter(icon)

        return remember(index, title, icon) {
            TabOptions(
                index = index,
                title = title,
                icon = painter
            )
        }
    }
}