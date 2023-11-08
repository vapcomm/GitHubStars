/*
 * (c) VAP Communications Group, 2023
 */

package online.vapcom.githubstars.utils

import kotlin.math.round

/**
 * Return shortened number of stars
 */
fun starsToShortString(stars: Long): String {
    return when (stars) {
        in 0..999 -> stars.toString()
        in 1000..999499 -> "${round(stars.toDouble() / 1000.0).toInt()}k"
        in 999500..999499999 -> "${round(stars.toDouble() / 1000000.0).toInt()}M"
        in 999500000..999499999999 -> "${round(stars.toDouble() / 1000000000.0).toInt()}G"
        else -> "1T+"
    }
}
