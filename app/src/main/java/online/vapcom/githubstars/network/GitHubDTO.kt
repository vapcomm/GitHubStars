/*
 * (c) VAP Communications Group, 2023
 */

package online.vapcom.githubstars.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Response to search repositories request.
 *
 * https://docs.github.com/en/rest/search/search?apiVersion=2022-11-28#search-repositories
 *
 * Empty result:
 * ```
 * {"total_count":0,"incomplete_results":false,"items":[]}
 * ```
 */
@Serializable
data class SearchReposResponse(
    @SerialName("total_count") val totalCount: Long = 0,
    @SerialName("incomplete_results") val incompleteResults: Boolean = false,
    val items: List<Repo> = emptyList()
) {
    /**
     * GitHub repository data, only used fields are left
     */
    @Serializable
    data class Repo(
        val id: Long,
        @SerialName("full_name") val fullName: String = "",
        val owner: RepoOwner = RepoOwner(),
        @SerialName("html_url") val htmlURL: String = "",
        val description: String = "",
        @SerialName("stargazers_count") val stars: Long = 0,
        val language: String = "",
        val forks: Long = 0,
    )

    /**
     * Repository owner, we use only his/her avatar
     */
    @Serializable
    data class RepoOwner(
        @SerialName("avatar_url") val avatarURL: String = ""
    )

}

/**
 * Response body on GitHub server error
 * ```
 * {"message":"Only the first 1000 search results are available","documentation_url":"https://docs.github.com/v3/search/"}
 * ```
 */
@Serializable
data class ErrorMessageResponse(
    val message: String = ""
)
