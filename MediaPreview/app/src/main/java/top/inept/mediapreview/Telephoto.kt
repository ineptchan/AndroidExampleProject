package top.inept.mediapreview

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil3.imageLoader
import me.saket.telephoto.zoomable.coil3.ZoomableAsyncImage

@Composable
fun TelephotoDemo() {
    ZoomableAsyncImage(
        modifier = Modifier.fillMaxSize(),
        model = R.drawable.wallhaven_d85ewm,
        imageLoader = LocalContext.current.imageLoader, // Optional.
        contentDescription = null
    )
}