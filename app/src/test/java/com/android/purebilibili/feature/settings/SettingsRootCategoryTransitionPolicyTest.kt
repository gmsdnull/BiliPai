package com.android.purebilibili.feature.settings

import android.os.Build
import com.android.purebilibili.core.ui.adaptive.MotionTier
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertTrue

class SettingsRootCategoryTransitionPolicyTest {

    @Test
    fun resolveSettingsRootBodyDestination_prefersSearchOverCategory() {
        val destination = resolveSettingsRootBodyDestination(
            searchQuery = "主题",
            activeCategory = SettingsRootCategory.APPEARANCE_INTERACTION
        )

        assertIs<SettingsRootBodyDestination.Search>(destination)
    }

    @Test
    fun resolveSettingsRootBodyDestination_usesCategoryWhenNotSearching() {
        val destination = resolveSettingsRootBodyDestination(
            searchQuery = "",
            activeCategory = SettingsRootCategory.CONTENT_PLAYBACK
        )

        val category = assertIs<SettingsRootBodyDestination.Category>(destination)
        assertEquals(SettingsRootCategory.CONTENT_PLAYBACK, category.category)
    }

    @Test
    fun resolveSettingsRootBodyDestination_defaultsToHome() {
        val destination = resolveSettingsRootBodyDestination(
            searchQuery = "",
            activeCategory = null
        )

        assertIs<SettingsRootBodyDestination.Home>(destination)
    }

    @Test
    fun resolveSettingsRootCategoryFadeMillis_respectsAnimationGate() {
        assertEquals(0, resolveSettingsRootCategoryFadeMillis(animationEnabled = false, reduceMotion = false))
        assertEquals(0, resolveSettingsRootCategoryFadeMillis(animationEnabled = true, reduceMotion = true))
        assertEquals(
            SETTINGS_ROOT_CATEGORY_FADE_MILLIS,
            resolveSettingsRootCategoryFadeMillis(animationEnabled = true, reduceMotion = false)
        )
    }

    @Test
    fun resolveSettingsRootCategoryContentTransform_disablesFadeWhenAnimationGateOff() {
        assertEquals(
            0,
            resolveSettingsRootCategoryFadeMillis(animationEnabled = false, reduceMotion = false)
        )
        assertEquals(
            0,
            resolveSettingsRootCategoryFadeMillis(animationEnabled = true, reduceMotion = true)
        )
    }

    @Test
    fun resolveSettingsRootCategoryMaxBlurRadiusDp_scalesWithMotionTier() {
        assertEquals(0f, resolveSettingsRootCategoryMaxBlurRadiusDp(MotionTier.Reduced))
        assertEquals(
            SETTINGS_ROOT_CATEGORY_MAX_BLUR_DP_NORMAL,
            resolveSettingsRootCategoryMaxBlurRadiusDp(MotionTier.Normal)
        )
        assertEquals(
            SETTINGS_ROOT_CATEGORY_MAX_BLUR_DP_ENHANCED,
            resolveSettingsRootCategoryMaxBlurRadiusDp(MotionTier.Enhanced)
        )
    }

    @Test
    fun resolveSettingsRootCategoryTransitionBlurEnabled_respectsPerformanceGates() {
        assertFalse(
            resolveSettingsRootCategoryTransitionBlurEnabled(
                animationEnabled = false,
                reduceMotion = false,
                sdkInt = Build.VERSION_CODES.S,
                motionTier = MotionTier.Normal
            )
        )
        assertFalse(
            resolveSettingsRootCategoryTransitionBlurEnabled(
                animationEnabled = true,
                reduceMotion = true,
                sdkInt = Build.VERSION_CODES.S,
                motionTier = MotionTier.Normal
            )
        )
        assertFalse(
            resolveSettingsRootCategoryTransitionBlurEnabled(
                animationEnabled = true,
                reduceMotion = false,
                sdkInt = Build.VERSION_CODES.R,
                motionTier = MotionTier.Normal
            )
        )
        assertFalse(
            resolveSettingsRootCategoryTransitionBlurEnabled(
                animationEnabled = true,
                reduceMotion = false,
                sdkInt = Build.VERSION_CODES.S,
                motionTier = MotionTier.Reduced
            )
        )
        assertTrue(
            resolveSettingsRootCategoryTransitionBlurEnabled(
                animationEnabled = true,
                reduceMotion = false,
                sdkInt = Build.VERSION_CODES.S,
                motionTier = MotionTier.Normal
            )
        )
    }

    @Test
    fun resolveSettingsRootCategoryExitBlurRadiusPx_growsWithExitProgressAndCapsRadius() {
        assertEquals(0f, resolveSettingsRootCategoryExitBlurRadiusPx(exitProgress = 0f, maxBlurRadiusPx = 24f))
        assertEquals(12f, resolveSettingsRootCategoryExitBlurRadiusPx(exitProgress = 0.5f, maxBlurRadiusPx = 24f))
        assertEquals(
            SETTINGS_ROOT_CATEGORY_MAX_BLUR_RADIUS_PX_CAP,
            resolveSettingsRootCategoryExitBlurRadiusPx(exitProgress = 1f, maxBlurRadiusPx = 40f)
        )
    }
}