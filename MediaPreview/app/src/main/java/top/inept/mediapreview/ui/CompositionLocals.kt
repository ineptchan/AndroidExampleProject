package top.inept.mediapreview.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf

val LocalBottomBarVisibility = compositionLocalOf<MutableState<Boolean>> {
    mutableStateOf(true)
}