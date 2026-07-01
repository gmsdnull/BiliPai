package com.android.purebilibili.feature.settings

internal fun resolvePredictiveBackStyleOptions(): List<PlaybackSegmentOption<String>> {
    return listOf(
        PlaybackSegmentOption("scale", "卡片缩放"),
        PlaybackSegmentOption("aosp", "系统跨页"),
        PlaybackSegmentOption("classic", "经典滑出"),
        PlaybackSegmentOption("default", "系统默认"),
    )
}

internal fun resolvePredictiveBackStyleLabel(storageValue: String): String {
    return resolvePredictiveBackStyleOptions()
        .firstOrNull { it.value == storageValue }
        ?.label
        ?: "卡片缩放"
}

internal fun resolvePredictiveBackExitDirectionOptions(): List<PlaybackSegmentOption<String>> {
    return listOf(
        PlaybackSegmentOption("auto", "跟随卡片"),
        PlaybackSegmentOption("follow_gesture", "跟随手势"),
        PlaybackSegmentOption("always_right", "始终向右"),
        PlaybackSegmentOption("always_left", "始终向左"),
    )
}

internal fun resolvePredictiveBackExitDirectionLabel(storageValue: String): String {
    return resolvePredictiveBackExitDirectionOptions()
        .firstOrNull { it.value == storageValue }
        ?.label
        ?: "跟随卡片"
}