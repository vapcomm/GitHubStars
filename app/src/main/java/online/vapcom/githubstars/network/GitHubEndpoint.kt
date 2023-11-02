/*
 * (c) VAP Communications Group, 2023
 */

package online.vapcom.githubstars.network

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import online.vapcom.githubstars.data.GitHubRepo
import online.vapcom.githubstars.data.Reply
import online.vapcom.githubstars.data.UIErrno
import online.vapcom.githubstars.data.moduleErrorCode

class GitHubEndpoint(val searchURL: String, private val httpClient: HttpClient = NetworkClientBuilder(TAG).build()) {

    companion object {
        private const val TAG = "GitHubEndp"
        private const val ERROR_MODULE_NUM = 50 // error code module number, see ErrorState

        private const val ERROR_SEARCH = 10
    }

    suspend fun getStarredReposList(): Reply<List<GitHubRepo>> {
        val url = "$searchURL/repositories?q=stars:>1&sort=stars&order=desc&per_page=5&page=1"

        return try {
             val response = httpClient.get(url) {
                contentType(ContentType.Application.Json)
            }

            if (response.status.isSuccess()) {
                val searchResponse: SearchReposResponse = response.body()
                Reply.Success(
                    searchResponse.items.map { r ->
                        GitHubRepo(
                            id = r.id,
                            name = r.fullName,
                            desc = r.description,
                            stars = r.stars,
                            forks = r.forks,
                            lang = r.language,
                            url = r.htmlURL,
                            iconURL = r.owner.avatarURL
                        )
                    }
                )
            } else {
                val error = httpStatusToErrorState(response.status, ERROR_MODULE_NUM)
                if (error.code == UIErrno.CONTENT_ERROR.errno) {
                    // try to get info message from response
                    val rsp: ErrorMessageResponse = response.body()
                    Reply.Error(error.copy(desc = rsp.message))
                } else Reply.Error(error)
            }

        } catch (ex: Exception) {
            Log.e(TAG, "Error: search repositories GET Request to '$url' error: $ex")
            Reply.Error(ktorExceptionToErrorState(url, ex, moduleErrorCode(ERROR_MODULE_NUM, ERROR_SEARCH)))
        }
    }

}
