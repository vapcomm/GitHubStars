/*
 * (c) VAP Communications Group, 2023
 */

package online.vapcom.githubstars.ui.list

import online.vapcom.githubstars.data.ErrorState
import online.vapcom.githubstars.data.GitHubRepo

data class RepoListState(
    val isLoading: Boolean = false,                 // show loading progress on true
    val repos: List<GitHubRepo> = emptyList(),      // starred repos list to show
    val error: ErrorState = ErrorState.NO_ERROR
)
