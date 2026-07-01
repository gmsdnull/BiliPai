package com.android.purebilibili.navigation

import com.android.purebilibili.core.store.HomeSettings
import com.android.purebilibili.core.theme.AndroidNativeVariant
import com.android.purebilibili.core.theme.UiPreset

internal data class AppNavigationAppearance(
    val cardTransitionEnabled: Boolean,
    val videoTransitionRealtimeBlurEnabled: Boolean,
    val bottomBarBlurEnabled: Boolean,
    val bottomBarLabelMode: Int,
    val bottomBarFloating: Boolean
)

internal fun resolveEffectiveNavigationBottomBarBlur(
    homeSettings: HomeSettings,
    uiPreset: UiPreset,
    androidNativeVariant: AndroidNativeVariant
): Boolean = when {
    uiPreset == UiPreset.MD3 &&
        androidNativeVariant == AndroidNativeVariant.MATERIAL3 &&
        !homeSettings.androidNativeLiquidGlassEnabled -> false
    else -> homeSettings.isBottomBarBlurEnabled
}

internal fun resolveAppNavigationAppearance(
    homeSettings: HomeSettings,
    uiPreset: UiPreset = UiPreset.IOS,
    androidNativeVariant: AndroidNativeVariant = AndroidNativeVariant.MATERIAL3
): AppNavigationAppearance {
    return AppNavigationAppearance(
        cardTransitionEnabled = homeSettings.cardTransitionEnabled,
        videoTransitionRealtimeBlurEnabled = homeSettings.videoTransitionRealtimeBlurEnabled,
        bottomBarBlurEnabled = resolveEffectiveNavigationBottomBarBlur(
            homeSettings = homeSettings,
            uiPreset = uiPreset,
            androidNativeVariant = androidNativeVariant
        ),
        bottomBarLabelMode = homeSettings.bottomBarLabelMode,
        bottomBarFloating = homeSettings.isBottomBarFloating
    )
}
