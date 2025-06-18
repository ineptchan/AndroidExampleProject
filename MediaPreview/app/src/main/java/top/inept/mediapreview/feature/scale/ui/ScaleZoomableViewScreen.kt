package top.inept.mediapreview.feature.scale.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.jvziyaoyao.scale.zoomable.zoomable.ZoomableGestureScope
import com.jvziyaoyao.scale.zoomable.zoomable.ZoomableView
import com.jvziyaoyao.scale.zoomable.zoomable.rememberZoomableState
import kotlinx.coroutines.launch
import top.inept.mediapreview.R
import top.inept.mediapreview.ui.LocalBottomBarVisibility

@Composable
fun ScaleZoomableViewScreen() {
    val bottomBarVisibility = LocalBottomBarVisibility.current

    DisposableEffect(Unit) {
        bottomBarVisibility.value = false
        onDispose {
            bottomBarVisibility.value = true
        }
    }

    val scope = rememberCoroutineScope()
    val painter = painterResource(id = R.drawable.wallhaven_d85gjg)
    val state = rememberZoomableState(contentSize = painter.intrinsicSize)

    ZoomableView(
        state = state,
        detectGesture = ZoomableGestureScope(
            onDoubleTap = {
                Log.d("Scale", "DetectGesture->DoubleTap")
                scope.launch { state.toggleScale(it) }
            },
            onTap = {
                Log.d("Scale", "DetectGesture->Tap")
            },
            onLongPress = {
                Log.d("Scale", "DetectGesture->LongPress")
            }
        )
    ) {
        Image(
            modifier = Modifier.fillMaxSize(), // 这里请务必要充满整个图层
            painter = painter,
            contentDescription = null,
        )
    }

    //detectGesture 可以注册一个检测手势
    //ZoomableGestureScope 可缩放的手势范围,onDoubleTap 双击触发,Tap 点击,LongPress 长按
}

