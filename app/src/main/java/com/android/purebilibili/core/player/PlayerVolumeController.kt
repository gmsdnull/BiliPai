package com.android.purebilibili.core.player

import androidx.media3.common.Player
import com.android.purebilibili.core.network.NetworkModule
import com.android.purebilibili.core.store.player.PlayerSettingsStore
import kotlin.math.roundToInt

object PlayerVolumeController {
    fun normalize(volume: Float): Float = PlayerSettingsStore.normalizePlayerVolume(volume)

    fun resolveFromGesture(
        startVolume: Float,
        totalDragDistanceY: Float,
        gestureHeightPx: Float,
        gestureSensitivity: Float
    ): Float {
        if (gestureHeightPx <= 0f) return normalize(startVolume)
        val delta = -totalDragDistanceY / gestureHeightPx * gestureSensitivity
        return normalize(startVolume + delta)
    }

    fun percent(volume: Float): Int = (normalize(volume) * 100f).roundToInt()

    fun preferredVolumeSync(): Float {
        val context = NetworkModule.appContext ?: return 1.0f
        return PlayerSettingsStore.getPreferredPlayerVolumeSync(context)
    }

    fun applyPreferredVolume(player: Player) {
        player.volume = preferredVolumeSync()
    }
}
