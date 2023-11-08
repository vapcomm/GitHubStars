/*
 * (c) VAP Communications Group, 2023
 */

package online.vapcom.githubstars.repo

import online.vapcom.githubstars.data.GitHubRepo

data class SearchReplyData(
    val totalFound: Long,
    val incompleteResults: Boolean,
    val currentPage: Int,
    val maxPage: Int,
    val reposPerPage: Int,
    val repos: List<GitHubRepo>
)
