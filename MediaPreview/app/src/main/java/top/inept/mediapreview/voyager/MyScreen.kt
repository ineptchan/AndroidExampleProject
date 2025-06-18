package top.inept.mediapreview.voyager

import androidx.compose.ui.graphics.vector.ImageVector
import cafe.adriel.voyager.core.screen.Screen

interface MyScreen : Screen {
    val name: Int
    val icon: ImageVector?
}
