package com.android.purebilibili.core.player

import kotlin.test.Test
import kotlin.test.assertEquals

class PlayerVolumeControllerTest {

    @Test
    fun `gesture volume changes in two percent steps`() {
        assertEquals(
            0.52f,
            PlayerVolumeController.resolveFromGesture(
                startVolume = 0.5f,
                totalDragDistanceY = -10f,
                gestureHeightPx = 500f,
                gestureSensitivity = 1f
            )
        )
        assertEquals(
            52,
            PlayerVolumeController.percent(0.519f)
        )
    }

    @Test
    fun `gesture volume is clamped to valid range`() {
        assertEquals(
            1f,
            PlayerVolumeController.resolveFromGesture(
                startVolume = 0.8f,
                totalDragDistanceY = -1_000f,
                gestureHeightPx = 500f,
                gestureSensitivity = 1f
            )
        )
        assertEquals(
            0f,
            PlayerVolumeController.resolveFromGesture(
                startVolume = 0.2f,
                totalDragDistanceY = 1_000f,
                gestureHeightPx = 500f,
                gestureSensitivity = 1f
            )
        )
    }
}
