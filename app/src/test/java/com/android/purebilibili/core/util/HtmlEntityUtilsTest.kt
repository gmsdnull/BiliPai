package com.android.purebilibili.core.util

import org.junit.Assert.assertEquals
import org.junit.Test

class HtmlEntityUtilsTest {

    @Test
    fun `unescape decodes single quote entity from bilibili comments`() {
        // 复现 #583：评论区单引号被编码为 &#39;
        assertEquals("I'm here", HtmlEntityUtils.unescape("I&#39;m here"))
    }

    @Test
    fun `unescape decodes single quote entity without trailing semicolon`() {
        assertEquals("I'm", HtmlEntityUtils.unescape("I&#39m"))
    }

    @Test
    fun `unescape decodes common named entities`() {
        assertEquals(
            "a & b < c > d \"e\" 'f'",
            HtmlEntityUtils.unescape("a &amp; b &lt; c &gt; d &quot;e&quot; &apos;f&apos;")
        )
    }

    @Test
    fun `unescape decodes decimal and hex numeric entities`() {
        assertEquals("'$", HtmlEntityUtils.unescape("&#39;&#36;"))
        assertEquals("★", HtmlEntityUtils.unescape("&#9733;"))
        assertEquals("★", HtmlEntityUtils.unescape("&#x2605;"))
    }

    @Test
    fun `unescape decodes supplementary code points as surrogate pairs`() {
        assertEquals("😀", HtmlEntityUtils.unescape("&#x1F600;"))
    }

    @Test
    fun `unescape leaves plain text and emote shortcodes untouched`() {
        assertEquals("hello [doge] 世界", HtmlEntityUtils.unescape("hello [doge] 世界"))
    }

    @Test
    fun `unescape leaves lone ampersand and unknown entities untouched`() {
        assertEquals("AT&T", HtmlEntityUtils.unescape("AT&T"))
        assertEquals("a &unknown; b", HtmlEntityUtils.unescape("a &unknown; b"))
    }

    @Test
    fun `unescape returns empty string for empty input`() {
        assertEquals("", HtmlEntityUtils.unescape(""))
    }
}
