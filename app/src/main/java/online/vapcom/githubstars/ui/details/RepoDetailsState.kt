/*
 * (c) VAP Communications Group, 2023
 */

package online.vapcom.githubstars.ui.details

import online.vapcom.githubstars.data.ErrorState
import online.vapcom.githubstars.data.GitHubRepo

data class RepoDetailsState(
    val isLoading: Boolean = false,
    val repo: GitHubRepo = GitHubRepo.EMPTY,
    val error: ErrorState = ErrorState.NO_ERROR
)
