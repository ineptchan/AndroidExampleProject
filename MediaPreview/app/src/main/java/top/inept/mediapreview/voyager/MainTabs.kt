package top.inept.mediapreview.voyager

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import top.inept.mediapreview.R
import top.inept.mediapreview.feature.scale.ui.ScaleScreen
import top.inept.mediapreview.utils.BuildTabOptions

object MainTabs {
    val tabs = listOf(ScaleTab)

    object ScaleTab : Tab {
        override val options: TabOptions
            @Composable
            get() = BuildTabOptions.buildTabOptions(0u, R.string.scale, Icons.Default.Image)

        @Composable
        override fun Content() = ScaleScreen()
    }
}