package com.android.purebilibili.feature.video.screen

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class VideoDetailRouteSheetPolicyTest {

    @Test
    fun homeSourceEnablesRouteSheetMotion() {
        val motion = resolveVideoDetailRouteSheetMotion(
            sourceRoute = "home",
            transitionEnabled = true
        )

        assertTrue(motion.enabled)
        assertEquals(360, motion.durationMillis)
        assertEquals(0.965f, motion.initialScale)
        assertEquals(28f, motion.initialCornerDp)
    }

    @Test
    fun nonHomeSourceDoesNotUseRouteSheetMotion() {
        assertFalse(
            resolveVideoDetailRouteSheetMotion(
                sourceRoute = "search",
                transitionEnabled = true
            ).enabled
        )
        assertFalse(
            resolveVideoDetailRouteSheetMotion(
                sourceRoute = "home",
                transitionEnabled = false
            ).enabled
        )
    }

    @Test
    fun routeSheetFrameStartsAsRoundedFloatingPanelAndEndsFullscreen() {
        val motion = resolveVideoDetailRouteSheetMotion(
            sourceRoute = "home",
            transitionEnabled = true
        )
        val start = resolveVideoDetailRouteSheetFrame(0f, motion)
        val end = resolveVideoDetailRouteSheetFrame(1f, motion)

        assertEquals(0.965f, start.scale)
        assertEquals(56f, start.translationYDp)
        assertEquals(28f, start.cornerDp)
        assertEquals(0.18f, start.backgroundScrimAlpha)

        assertEquals(1f, end.scale)
        assertEquals(0f, end.translationYDp)
        assertEquals(0f, end.cornerDp)
        assertEquals(0f, end.backgroundScrimAlpha)
    }
}
