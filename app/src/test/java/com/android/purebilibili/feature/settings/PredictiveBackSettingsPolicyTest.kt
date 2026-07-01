package com.android.purebilibili.feature.settings

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PredictiveBackSettingsPolicyTest {

    @Test
    fun styleOptions_includeAllSupportedStyles() {
        val values = resolvePredictiveBackStyleOptions().map { it.value }.toSet()
        assertEquals(setOf("scale", "aosp", "classic", "default"), values)
    }

    @Test
    fun styleLabel_resolvesKnownValues() {
        assertEquals("卡片缩放", resolvePredictiveBackStyleLabel("scale"))
        assertEquals("系统跨页", resolvePredictiveBackStyleLabel("aosp"))
        assertEquals("经典滑出", resolvePredictiveBackStyleLabel("classic"))
        assertEquals("系统默认", resolvePredictiveBackStyleLabel("default"))
    }

    @Test
    fun styleLabel_fallsBackForUnknownValue() {
        assertEquals("卡片缩放", resolvePredictiveBackStyleLabel("unknown"))
    }

    @Test
    fun exitDirectionOptions_includeAllSupportedModes() {
        val values = resolvePredictiveBackExitDirectionOptions().map { it.value }.toSet()
        assertEquals(setOf("auto", "follow_gesture", "always_right", "always_left"), values)
    }

    @Test
    fun exitDirectionLabel_resolvesKnownValues() {
        assertEquals("跟随卡片", resolvePredictiveBackExitDirectionLabel("auto"))
        assertEquals("跟随手势", resolvePredictiveBackExitDirectionLabel("follow_gesture"))
        assertEquals("始终向右", resolvePredictiveBackExitDirectionLabel("always_right"))
        assertEquals("始终向左", resolvePredictiveBackExitDirectionLabel("always_left"))
    }

    @Test
    fun exitDirectionLabel_fallsBackForUnknownValue() {
        assertEquals("跟随卡片", resolvePredictiveBackExitDirectionLabel("unknown"))
    }
}