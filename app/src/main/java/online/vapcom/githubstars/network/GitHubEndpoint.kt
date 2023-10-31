/*
 * (c) VAP Communications Group, 2023
 */

package online.vapcom.githubstars.network

import io.ktor.client.HttpClient

class GitHubEndpoint(val gitHubURL: String, private val httpClient: HttpClient = NetworkClientBuilder(TAG).build()) {

    companion object {
        private const val TAG = "GitHubEndp"
        private const val ERROR_MODULE_NUM = 50 // error code module number, see ErrorState
    }


}
