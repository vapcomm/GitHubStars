/*
 * (c) VAP Communications Group, 2023
 */

package online.vapcom.githubstars.network

import online.vapcom.githubstars.data.GitHubRepo

data class SearchResult(
    val totalCount: Long,
    val incompleteResults: Boolean,
    val repos: List<GitHubRepo>
)
