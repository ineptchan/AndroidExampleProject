package top.inept.exoplayer.player

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class PlayerViewmodel @Inject constructor(
    @ApplicationContext private val context: Context,
) : ViewModel() {
    val exoPlayer: ExoPlayer by lazy {
        ExoPlayer.Builder(context).build().apply {
            // 可选：全局配置、监听器等
        }
    }

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun prepareFromUrls(vararg urls: String) {
        prepareFromUrls(urls.map { it })
    }

    fun prepareFromUrls(urls: List<String>) {
        val mediaItems = urls.map { MediaItem.fromUri(it) }
        exoPlayer.addMediaItems(mediaItems)
        exoPlayer.prepare()
    }

    fun prepareFromUris(vararg uris: Uri) {
        prepareFromUris(uris.map { it })
    }

    fun prepareFromUris(uris: List<Uri>) {
        val mediaItems = uris.map { MediaItem.fromUri(it) }
        exoPlayer.addMediaItems(mediaItems)
        exoPlayer.prepare()
    }


    fun play() = exoPlayer.play()

    fun pause() = exoPlayer.pause()

    override fun onCleared() {
        super.onCleared()
        exoPlayer.release()
    }

    fun updateMediaList(mediaList: List<Uri>) {
        _uiState.update { it.copy(mediaList = mediaList) }
        prepareFromUris(mediaList)
    }
}

data class UiState(
    val mediaList: List<Uri> = listOf(),
)