package top.inept.exoplayer.player

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.ui.PlayerView

@Composable
fun PlayerScreen(
    modifier: Modifier = Modifier,
    playerViewmodel: PlayerViewmodel = hiltViewModel(),
) {
    /*    LaunchedEffect(Unit) {
            playerViewmodel.prepareFromUrls("https://www.xbext.com/download/trailer.mp4")
            playerViewmodel.prepareFromUrlse("https://web-ch.scu.edu.tw/storage/app/uploads/public/584/a04/fce/584a04fceb9e7598678467.mp4")
        }*/

    val uiState by playerViewmodel.uiState.collectAsState()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia() // 使用 PickMultipleVisualMedia 合约来选择多个图片或视频
    ) { uris ->
        playerViewmodel.updateMediaList((uiState.mediaList + uris).distinct())
    }

    Column(modifier = modifier) {
        AndroidView(
            factory = { ctx ->
                PlayerView(ctx).apply {
                    player = playerViewmodel.exoPlayer
                    useController = true
                }
            },
            modifier = Modifier.aspectRatio(),
            update = { payerView ->
                payerView.player = playerViewmodel.exoPlayer
            }
        )

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.VideoOnly))
            }
        ) { Text("添加视频到播放列表") }
    }

    DisposableEffect(Unit) {
        onDispose {
            playerViewmodel.pause()
        }
    }
}