package top.inept.mediapreview.feature.scale.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.jvziyaoyao.scale.image.pager.ImagePager
import com.jvziyaoyao.scale.zoomable.pager.rememberZoomablePagerState
import kotlinx.coroutines.launch
import top.inept.mediapreview.R
import top.inept.mediapreview.ui.LocalBottomBarVisibility

@Composable
fun ScaleImagePagerScreen() {
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

    val pagerState = rememberZoomablePagerState { images.size }

    ImagePager(
        pagerState = pagerState,
        imageLoader = { index ->
            val painter = painterResource(images[index])
            Pair(painter, painter.intrinsicSize)
        },  //可以使用Coli的rememberAsyncImagePainter 进行网络加载或者本地加载
        imageLoading = {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.Blue,
                )
            }
        },  //imageLoading图片加载动画
        pageDecoration = { page, innerPage ->
            //page第几页
            //innerPage 图片内容Composable
            Box(modifier = Modifier.fillMaxSize()) {
                innerPage.invoke()

                ImagePageTop()

                ImagePageBottom(
                    page = page,
                    backPage = { scope.launch { pagerState.animateScrollToPage(page - 1, 0f) } },
                    nextPage = { scope.launch { pagerState.animateScrollToPage(page + 1, 0f) } }
                )

            }
        }   //自定义ImagePage页面
    )
}

@Composable
private fun BoxScope.ImagePageTop(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .padding(top = 20.dp)
            .padding(8.dp)
            .align(Alignment.TopCenter),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(
                alpha = 0.6f
            )
        )   //Card透明背景
    ) {
        Text(
            text = "这里可以是文件名字",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
private fun BoxScope.ImagePageBottom(
    modifier: Modifier = Modifier,
    page: Int,
    backPage: () -> Unit,
    nextPage: () -> Unit,
) {
    Card(
        modifier = modifier
            .padding(bottom = 20.dp)
            .padding(8.dp)
            .align(Alignment.BottomCenter),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(
                alpha = 0.6f
            )
        )   //Card透明背景
    ) {
        Row {
            IconButton(onClick = backPage) { Icon(Icons.Default.ArrowBack, null) }

            Text(
                text = page.toString(),
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.align(Alignment.CenterVertically)
            )

            IconButton(onClick = nextPage) { Icon(Icons.Default.ArrowForward, null) }
        }
    }
}
