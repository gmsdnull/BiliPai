package com.android.purebilibili.feature.settings.ui

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import com.android.purebilibili.core.ui.motion.AppMotionEasing
import com.android.purebilibili.feature.settings.SETTINGS_ROOT_CATEGORY_FADE_MILLIS
import com.android.purebilibili.feature.settings.SETTINGS_ROOT_CATEGORY_MAX_BLUR_RADIUS_PX_CAP

/**
 * 仅对 [AnimatedContent] 中正在退出的页面施加轻量模糊，进入页保持清晰。
 * 模糊通过 [graphicsLayer] + [RenderEffect] 在 GPU 层合成，避免触发布局。
 */
@Composable
fun AnimatedContentScope.settingsRootCategoryExitBlurModifier(
    contentKey: Any,
    blurEnabled: Boolean,
    maxBlurRadiusPx: Float
): Modifier {
    if (!blurEnabled || maxBlurRadiusPx <= 0f) return Modifier

    val blurRadiusPx by transition.animateFloat(
        transitionSpec = {
            if (contentKey == initialState && targetState != contentKey) {
                tween(
                    durationMillis = SETTINGS_ROOT_CATEGORY_FADE_MILLIS,
                    easing = AppMotionEasing.EmphasizedExit
                )
            } else {
                snap()
            }
        },
        label = "settingsRootCategoryExitBlur"
    ) { state ->
        if (state == contentKey && transition.targetState != contentKey) {
            maxBlurRadiusPx
        } else {
            0f
        }
    }

    val cappedBlurRadiusPx = blurRadiusPx.coerceIn(0f, SETTINGS_ROOT_CATEGORY_MAX_BLUR_RADIUS_PX_CAP)

    return Modifier.graphicsLayer {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && cappedBlurRadiusPx > 0.5f) {
            renderEffect = RenderEffect.createBlurEffect(
                cappedBlurRadiusPx,
                cappedBlurRadiusPx,
                Shader.TileMode.CLAMP
            ).asComposeRenderEffect()
        } else {
            renderEffect = null
        }
    }
}