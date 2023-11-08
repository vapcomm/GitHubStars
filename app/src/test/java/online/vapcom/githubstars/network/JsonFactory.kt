/*
 * (c) VAP Communications Group, 2023
 */

package online.vapcom.githubstars.network

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

/**
 * One JSON serializer config for all
 */
object JsonFactory {
    @OptIn(ExperimentalSerializationApi::class)
    val json = Json {
        coerceInputValues = true
        ignoreUnknownKeys = true
        encodeDefaults = true
        explicitNulls = false
    }
}
