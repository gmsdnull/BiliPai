package com.android.purebilibili.navigation3.predictiveback

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.scene.Scene
import androidx.navigation3.ui.defaultTransitionSpec
import androidx.navigationevent.NavigationEventTransitionState
import com.android.purebilibili.navigation3.BiliPaiNavKey
import com.android.purebilibili.navigation3.BiliPaiNavRouteTransition
import com.android.purebilibili.navigation3.resolveBiliPaiNavContentTransform

/**
 * 关闭共享元素(cardTransitionEnabled=OFF)时 VideoDetail → 卡片来源页的返回。
 *
 * 预测式返回手势与实体/系统返回键都复用同一条 [resolveBiliPaiNavContentTransform] 的方向化横滑
 * (`CARD_DISABLED_VIDEO_RETURN_TO_LEFT/RIGHT`)，避免"手势预览走 AOSP 横滑淡入、松手/按钮返回
 * 却是方向化整宽横滑"的观感分裂。方向由卡片来源位置(左/右)决定，不随手势边缘变化，保证返回
 * 朝向卡片实际所在方向一致。
 */
internal class BiliPaiCardDisabledReturnPredictiveBackAnimation(
    private val routeTransition: BiliPaiNavRouteTransition,
) : BiliPaiPredictiveBackAnimationHandler {
    override suspend fun onBackPressed(
        transitionState: NavigationEventTransitionState?,
        currentPageKey: BiliPaiNavKey?,
    ) = Unit

    @Composable
    override fun Modifier.predictiveBackAnimationDecorator(
        transitionState: NavigationEventTransitionState?,
        contentPageKey: Any,
        currentPageKey: BiliPaiNavKey?,
    ): Modifier = this

    override fun AnimatedContentTransitionScope<Scene<BiliPaiNavKey>>.onPredictivePopTransitionSpec(
        swipeEdge: Int,
    ): ContentTransform = resolveBiliPaiNavContentTransform(routeTransition)

    override fun AnimatedContentTransitionScope<Scene<BiliPaiNavKey>>.onPopTransitionSpec(): ContentTransform =
        resolveBiliPaiNavContentTransform(routeTransition)

    override fun AnimatedContentTransitionScope<Scene<BiliPaiNavKey>>.onTransitionSpec(): ContentTransform =
        defaultTransitionSpec<BiliPaiNavKey>().invoke(this)
}
