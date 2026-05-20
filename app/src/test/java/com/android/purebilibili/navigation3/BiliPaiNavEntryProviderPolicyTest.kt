package com.android.purebilibili.navigation3

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class BiliPaiNavEntryProviderPolicyTest {

    @Test
    fun sharedReadyMetadataDisablesRouteLayerForReturnTarget() {
        val metadata = biliPaiNavEntryMetadata(
            key = BiliPaiNavKey.Home,
            sourceMetadata = BiliPaiNavSourceMetadata(
                sourceKey = "home:BV1",
                sourceRoute = "home",
                clickedBoundsRecorded = true,
                cardFullyVisible = true
            )
        )

        assertTrue(metadata.isNotEmpty())
        assertEquals(3, metadata.size)
    }

    @Test
    fun homeVideoPushUsesSheetRouteLayerWithRecordedBounds() {
        val transitions = resolveBiliPaiNavEntryRouteTransitions(
            key = BiliPaiNavKey.VideoDetail(bvid = "BV1", sourceRoute = "home"),
            sourceMetadata = BiliPaiNavSourceMetadata(
                sourceKey = "home:BV1",
                sourceRoute = "home",
                clickedBoundsRecorded = true,
                cardFullyVisible = true
            )
        )

        assertEquals(BiliPaiNavRouteTransition.HOME_VIDEO_SHEET_FORWARD, transitions.forward)
        assertEquals(BiliPaiNavRouteTransition.HOME_VIDEO_SHEET_RETURN, transitions.pop)
        assertEquals(BiliPaiNavRouteTransition.HOME_VIDEO_SHEET_RETURN, transitions.predictivePop)
    }

    @Test
    fun homeVideoPushWithTopChromeOverlapStillUsesSheetWhenBoundsAreRecorded() {
        val transitions = resolveBiliPaiNavEntryRouteTransitions(
            key = BiliPaiNavKey.VideoDetail(bvid = "BV1", sourceRoute = "home"),
            sourceMetadata = BiliPaiNavSourceMetadata(
                sourceKey = "home:BV1",
                sourceRoute = "home",
                clickedBoundsRecorded = true,
                cardFullyVisible = false
            )
        )

        assertEquals(BiliPaiNavRouteTransition.HOME_VIDEO_SHEET_FORWARD, transitions.forward)
        assertEquals(BiliPaiNavRouteTransition.HOME_VIDEO_SHEET_RETURN, transitions.pop)
        assertEquals(BiliPaiNavRouteTransition.HOME_VIDEO_SHEET_RETURN, transitions.predictivePop)
    }

    @Test
    fun homeVideoPushWithoutRecordedBoundsKeepsForwardFallback() {
        val transitions = resolveBiliPaiNavEntryRouteTransitions(
            key = BiliPaiNavKey.VideoDetail(bvid = "BV1", sourceRoute = "home"),
            sourceMetadata = BiliPaiNavSourceMetadata(
                sourceKey = "home:BV1",
                sourceRoute = "home",
                clickedBoundsRecorded = false,
                cardFullyVisible = true
            )
        )

        assertEquals(BiliPaiNavRouteTransition.FALLBACK, transitions.forward)
        assertEquals(BiliPaiNavRouteTransition.NO_OP_SHARED_ELEMENT, transitions.pop)
        assertEquals(BiliPaiNavRouteTransition.NO_OP_SHARED_ELEMENT, transitions.predictivePop)
    }

    @Test
    fun videoPushWithStaleSharedSourceKeepsForwardFallback() {
        val transitions = resolveBiliPaiNavEntryRouteTransitions(
            key = BiliPaiNavKey.VideoDetail(bvid = "BV2", sourceRoute = "home"),
            sourceMetadata = BiliPaiNavSourceMetadata(
                sourceKey = "home:BV1",
                sourceRoute = "home",
                clickedBoundsRecorded = true,
                cardFullyVisible = true
            )
        )

        assertEquals(BiliPaiNavRouteTransition.FALLBACK, transitions.forward)
        assertEquals(BiliPaiNavRouteTransition.NO_OP_SHARED_ELEMENT, transitions.pop)
        assertEquals(BiliPaiNavRouteTransition.NO_OP_SHARED_ELEMENT, transitions.predictivePop)
    }

    @Test
    fun providerUsesTypedVideoEntryContentKey() {
        val provider = biliPaiNavEntryProvider(
            sourceMetadata = BiliPaiNavSourceMetadata(),
            content = {}
        )
        val key = BiliPaiNavKey.VideoDetail(bvid = "BV1", sourceRoute = "search")
        val entry = provider(key)

        assertEquals(key.toString(), entry.contentKey)
        assertTrue(entry.metadata.isNotEmpty())
    }
}
