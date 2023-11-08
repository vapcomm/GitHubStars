/*
 * (c) VAP Communications Group, 2023
 */

package online.vapcom.githubstars

import org.junit.Test
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.test.KoinTest
import org.koin.test.verify.verify

/**
 * Check Koin module configuration
 *
 * https://insert-koin.io/docs/quickstart/android-compose/#checking-your-modules
 * https://insert-koin.io/docs/reference/koin-test/checkmodules
 */
class KoinModulesTest : KoinTest {

    @OptIn(KoinExperimentalAPI::class)
    @Test
    fun checkAllModules() {
        appModule.verify(extraTypes = listOf(
            io.ktor.client.engine.HttpClientEngine::class,
            io.ktor.client.HttpClientConfig::class
        ))
    }

}
