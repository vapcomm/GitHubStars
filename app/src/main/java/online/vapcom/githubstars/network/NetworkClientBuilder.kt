/*
 * (c) VAP Communications Group, 2023
 */

package online.vapcom.githubstars.network

import android.util.Log
import io.ktor.client.*
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

/**
 * Configure HttpClient
 */
class NetworkClientBuilder(private val logTag: String = TAG, private val createClient: () -> HttpClient = ::createHttpClient) {

    companion object {
        private const val TAG = "NetClient"
        private const val USER_AGENT = "GitHubStars"
        // TODO: setup user-agent from system/app dependent function
    }

    /**
     * Local logger for HttpClient
     */
    class EndpointLogger(private val tag: String) : Logger {
        override fun log(message: String) {
            Log.d(tag, message)
        }
    }

    fun build() = createClient().config {
        install(Logging) {
            logger = EndpointLogger(logTag)
            level = LogLevel.ALL // INFO // ALL
        }

        install(ContentNegotiation) {
            json(
                Json {
                    coerceInputValues = true
                    ignoreUnknownKeys = true
                    encodeDefaults = true
                }
            )
        }

        install(UserAgent) {
            agent = USER_AGENT
        }

        install(HttpTimeout) {
            requestTimeoutMillis = 10000    // full request process timeout
            connectTimeoutMillis = 5000     // connection setup timeout
        }
    }

}

/**
 * System-dependant HttpClient creation.
 * For KMM project should be implemented as expect/actual functions.
 */
fun createHttpClient(): HttpClient {
    return HttpClient(OkHttp) {
        engine {
            config {
                retryOnConnectionFailure(true)
                // add more config options here if needed
            }
        }
    }
}
