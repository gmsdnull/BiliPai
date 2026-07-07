package com.android.purebilibili.navigation3.predictiveback

import com.android.purebilibili.navigation3.BiliPaiNavRouteTransition

internal fun resolveBiliPaiPredictiveBackAnimationHandler(
    routeTransition: BiliPaiNavRouteTransition,
    predictiveBackEnabled: Boolean = true,
    style: BiliPaiPredictiveBackAnimationStyle = BiliPaiPredictiveBackAnimationStyle.SCALE,
    exitDirection: BiliPaiPredictiveBackExitDirection = BiliPaiPredictiveBackExitDirection.ALWAYS_RIGHT,
): BiliPaiPredictiveBackAnimationHandler {
    if (routeTransition == BiliPaiNavRouteTransition.NO_OP_SHARED_ELEMENT) {
        return BiliPaiSharedElementPredictiveBackAnimation()
    }

    if (!predictiveBackEnabled) {
        return BiliPaiDisabledPredictiveBackAnimation()
    }

    return when (routeTransition) {
        BiliPaiNavRouteTransition.SETTINGS_IOS_PUSH_POP ->
            BiliPaiIosPushPredictiveBackAnimation(exitDirection = exitDirection)
        BiliPaiNavRouteTransition.CLASSIC_CARD -> resolveClassicCardPredictiveBackHandler(
            style = style,
            exitDirection = exitDirection,
        )
        // 关闭共享元素时 VideoDetail → 卡片来源页：手势预览与按钮返回复用同一方向化横滑，
        // 避免"预测手势 AOSP 横滑淡入 vs 提交方向化整宽横滑"的观感分裂。
        BiliPaiNavRouteTransition.CARD_DISABLED_VIDEO_RETURN_TO_LEFT,
        BiliPaiNavRouteTransition.CARD_DISABLED_VIDEO_RETURN_TO_RIGHT ->
            BiliPaiCardDisabledReturnPredictiveBackAnimation(routeTransition)
        else -> BiliPaiDefaultPredictiveBackAnimation()
    }
}

private fun resolveClassicCardPredictiveBackHandler(
    style: BiliPaiPredictiveBackAnimationStyle,
    exitDirection: BiliPaiPredictiveBackExitDirection,
): BiliPaiPredictiveBackAnimationHandler {
    return when (style) {
        BiliPaiPredictiveBackAnimationStyle.DEFAULT ->
            BiliPaiDefaultPredictiveBackAnimation()
        BiliPaiPredictiveBackAnimationStyle.SCALE ->
            BiliPaiScalePredictiveBackAnimation(exitDirection)
        BiliPaiPredictiveBackAnimationStyle.AOSP ->
            BiliPaiAospCrossActivityPredictiveBackAnimation(exitDirection)
        BiliPaiPredictiveBackAnimationStyle.CLASSIC ->
            BiliPaiClassicPredictiveBackAnimation()
    }
}
