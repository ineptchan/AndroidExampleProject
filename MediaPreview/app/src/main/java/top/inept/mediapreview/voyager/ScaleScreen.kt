package top.inept.mediapreview.voyager

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import top.inept.mediapreview.R
import top.inept.mediapreview.feature.scale.ui.ScaleHomeScreen
import top.inept.mediapreview.feature.scale.ui.ScaleImageListScreen
import top.inept.mediapreview.feature.scale.ui.ScaleImagePagerScreen
import top.inept.mediapreview.feature.scale.ui.ScaleZoomableViewScreen

object ScaleScreen {
    val screenList = listOf(ZoomableView, ImagePager,ImageList)

    object Home : MyScreen {
        override val name: Int = R.string.scale_home
        override val icon: ImageVector? = null

        @Composable
        override fun Content() = ScaleHomeScreen()
    }


    object ZoomableView : MyScreen {
        override val name: Int = R.string.scale_zoomable_view
        override val icon: ImageVector? = null

        @Composable
        override fun Content() = ScaleZoomableViewScreen()
    }

    object ImagePager : MyScreen {
        override val name: Int = R.string.scale_image_pager
        override val icon: ImageVector? = null

        @Composable
        override fun Content() = ScaleImagePagerScreen()
    }

    object ImageList : MyScreen {
        override val name: Int = R.string.scale_image_list
        override val icon: ImageVector? = null

        @Composable
        override fun Content() = ScaleImageListScreen()
    }
}