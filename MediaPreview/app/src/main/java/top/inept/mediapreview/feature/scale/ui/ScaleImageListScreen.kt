package top.inept.mediapreview.feature.scale.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import com.jvziyaoyao.scale.image.previewer.ImagePreviewer
import com.jvziyaoyao.scale.image.previewer.TransformImageView
import com.jvziyaoyao.scale.zoomable.previewer.TransformPreviewerState
import com.jvziyaoyao.scale.zoomable.previewer.rememberPreviewerState
import kotlinx.coroutines.launch
import top.inept.mediapreview.R
import top.inept.mediapreview.ui.LocalBottomBarVisibility

@Composable
fun ScaleImageListScreen() {
    val bottomBarVisibility = LocalBottomBarVisibility.current

    DisposableEffect(Unit) {
        bottomBarVisibility.value = false
        onDispose {
            bottomBarVisibility.value = true
        }
    }

    val scope = rememberCoroutineScope()

    val images = remember {
        mutableStateListOf(
            R.drawable.wallhaven_d85gjg,
            R.drawable.wallhaven_lyq8mp,
            R.drawable.wallhaven_d85ewm
        )
    }
    val state = rememberPreviewerState(
        pageCount = { images.size },
        getKey = { index -> images[index] }
    )

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            itemsIndexed(images) { index, image ->
                ImageItem(
                    image = image,
                    onClick = { scope.launch { state.enterTransform(index) } },
                    transformState = state
                )
            }
        }

        ImagePreviewer(
            state = state,
            imageLoader = { page ->
                val painter = rememberAsyncImagePainter(model = images[page])   //加载完整图片
                Pair(painter, painter.intrinsicSize)
            },
        )
    }
}

@Composable
fun ImageItem(
    modifier: Modifier = Modifier,
    image: Any,
    onClick: () -> Unit,
    transformState: TransformPreviewerState,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick
    ) {
        Row(modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)) {
            TransformImageView(
                imageLoader = {
                    val painter = rememberAsyncImagePainter(
                        ImageRequest.Builder(LocalContext.current)
                            .data(image)
                            .size(200, 200) //缩略图设置大小
                            .build()
                    )
                    //第一个是PreviewerState设置的key，第二个是图片，第三个是图片大小
                    Triple(image, painter, painter.intrinsicSize)
                },
                transformState = transformState,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .height(32.dp)
                    .width(64.dp)
            )

            Spacer(Modifier.width(8.dp))
            Column(verticalArrangement = Arrangement.Center) {
                Text(
                    text = "文件名称",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "2025年7月4日",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
