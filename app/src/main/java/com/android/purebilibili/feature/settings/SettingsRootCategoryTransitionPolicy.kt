package com.android.purebilibili.feature.settings

import android.os.Build
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import com.android.purebilibili.core.ui.adaptive.MotionTier
import com.android.purebilibili.core.ui.motion.AppMotionEasing

internal const val SETTINGS_ROOT_CATEGORY_FADE_MILLIS = 220
internal const val SETTINGS_ROOT_CATEGORY_MAX_BLUR_DP_NORMAL = 6f
internal const val SETTINGS_ROOT_CATEGORY_MAX_BLUR_DP_ENHANCED = 8f
internal const val SETTINGS_ROOT_CATEGORY_MIN_BLUR_API = Build.VERSION_CODES.S
internal const val SETTINGS_ROOT_CATEGORY_MAX_BLUR_RADIUS_PX_CAP = 18f

internal sealed interface SettingsRootBodyDestination {
    data object Home : SettingsRootBodyDestination
    data class Category(val category: SettingsRootCategory) : SettingsRootBodyDestination
    data object Search : SettingsRootBodyDestination
}

internal fun resolveSettingsRootBodyDestination(
    searchQuery: String,
    activeCategory: SettingsRootCategory?
): SettingsRootBodyDestination = when {
    searchQuery.isNotBlank() -> SettingsRootBodyDestination.Search
    activeCategory != null -> SettingsRootBodyDestination.Category(activeCategory)
    else -> SettingsRootBodyDestination.Home
}

internal fun resolveSettingsRootCategoryFadeMillis(
    animationEnabled: Boolean,
    reduceMotion: Boolean
): Int = if (!animationEnabled || reduceMotion) 0 else SETTINGS_ROOT_CATEGORY_FADE_MILLIS

internal fun resolveSettingsRootCategoryMaxBlurRadiusDp(motionTier: MotionTier): Float =
    when (motionTier) {
        MotionTier.Reduced -> 0f
        MotionTier.Normal -> SETTINGS_ROOT_CATEGORY_MAX_BLUR_DP_NORMAL
        MotionTier.Enhanced -> SETTINGS_ROOT_CATEGORY_MAX_BLUR_DP_ENHANCED
    }

internal fun resolveSettingsRootCategoryTransitionBlurEnabled(
    animationEnabled: Boolean,
    reduceMotion: Boolean,
    sdkInt: Int,
    motionTier: MotionTier
): Boolean {
    if (!animationEnabled || reduceMotion) return false
    if (sdkInt < SETTINGS_ROOT_CATEGORY_MIN_BLUR_API) return false
    return resolveSettingsRootCategoryMaxBlurRadiusDp(motionTier) > 0f
}

internal fun resolveSettingsRootCategoryExitBlurRadiusPx(
    exitProgress: Float,
    maxBlurRadiusPx: Float
): Float {
    if (maxBlurRadiusPx <= 0f) return 0f
    return (maxBlurRadiusPx * exitProgress.coerceIn(0f, 1f))
        .coerceAtMost(SETTINGS_ROOT_CATEGORY_MAX_BLUR_RADIUS_PX_CAP)
}

internal fun resolveSettingsRootCategoryContentTransform(
    animationEnabled: Boolean,
    reduceMotion: Boolean
): ContentTransform {
    val millis = resolveSettingsRootCategoryFadeMillis(animationEnabled, reduceMotion)
    if (millis <= 0) {
        return EnterTransition.None togetherWith ExitTransition.None
    }
    return fadeIn(
        animationSpec = tween(
            durationMillis = millis,
            easing = AppMotionEasing.EmphasizedEnter
        )
    ) togetherWith fadeOut(
        animationSpec = tween(
            durationMillis = millis,
            easing = AppMotionEasing.EmphasizedExit
        )
    )
}