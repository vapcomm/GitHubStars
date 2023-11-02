/*
 * (c) VAP Communications Group, 2023
 */

package online.vapcom.githubstars.utils

import kotlin.math.round
import kotlin.test.Test
import kotlin.test.assertEquals

class UtilsTest {

    @Test
    fun starsToShortStringTest() {
        assertEquals("0", starsToShortString(0))
        assertEquals("1", starsToShortString(1))
        assertEquals("999", starsToShortString(999))
        assertEquals("1k", starsToShortString(1000))
        assertEquals("15k", starsToShortString(14501))
        assertEquals("999k", starsToShortString(999499))
        assertEquals("1M", starsToShortString(999999))
        assertEquals("1M", starsToShortString(1000000))
        assertEquals("999M", starsToShortString(999499999))
        assertEquals("1G", starsToShortString(1000000000))
        assertEquals("1G", starsToShortString(1000000001))
        assertEquals("999G", starsToShortString(999499999999))
        assertEquals("1T+", starsToShortString(1000000000000))
        assertEquals("1T+", starsToShortString(10000000000000))
    }
}