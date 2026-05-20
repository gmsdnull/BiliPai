package com.android.purebilibili.core.ui.transition

import androidx.compose.runtime.compositionLocalOf

internal data class VideoCardReturnTransitionState(
    val sourceKey: String? = null,
    val sourceRoute: String? = null,
    val isReturningFromDetail: Boolean = false,
    val sharedTransitionReady: Boolean = false
)

internal val LocalVideoCardSharedElementSourceRoute = compositionLocalOf<String?> { null }

internal val LocalVideoCardReturnTransitionState = compositionLocalOf {
    VideoCardReturnTransitionState()
}
