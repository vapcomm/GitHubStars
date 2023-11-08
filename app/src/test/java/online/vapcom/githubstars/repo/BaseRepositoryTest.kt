/*
 * (c) VAP Communications Group, 2023
 */

import io.ktor.client.HttpClient
import io.ktor.client.engine.*
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.UserAgent
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import online.vapcom.githubstars.network.JsonFactory
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds


open class BaseRepositoryTest(mockEngine: HttpClientEngine) {

    protected val testScope = TestScope()

    class TestCancellationException : CancellationException("Run test - OK!")

    protected val httpClient = mockedHttpClient { mockEngine }

    /**
     * Tests runner with coroutines.
     *
     * NOTE: if repository has coroutines launched in it (i.e. DevicesRepositoryImpl), simple runTest() can't finish at all,
     *      so we should stop it manually with try/catch
     */
    protected fun runRepoTest(timeout: Duration = 2000.milliseconds, unitTest: suspend () -> Unit) {
        try {
            // wait maximum 2 seconds for a one unit test's coroutines completion
            testScope.runTest(timeout = timeout) {
                unitTest()
                cancel()
            }
        } catch (ex: CancellationException) {
            println("Test cancelled by timeout")
        }
    }

    private fun mockedHttpClient(mockEngine: () -> HttpClientEngine)  =  HttpClient(mockEngine()) {
        install(ContentNegotiation) {
            json(JsonFactory.json)
        }

        install(Logging) {
            logger = object: Logger {
                override fun log(message: String) {
                    println("MockHTTP: $message")
                }
            }
            level = LogLevel.ALL
        }

        install(HttpTimeout) {
            // different odd timeouts to differentiate what happened
            connectTimeoutMillis = 7000
            requestTimeoutMillis = 9000
        }

        install(UserAgent) {
            agent = "RepositoryTest"
        }
    }

}
