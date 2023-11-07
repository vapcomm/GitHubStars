/*
 * (c) VAP Communications Group, 2023
 */

package online.vapcom.githubstars.ui.list

import online.vapcom.githubstars.data.ErrorState
import online.vapcom.githubstars.data.GitHubRepo

data class RepoListState(
    val isLoading: Boolean = false,                 // show loading progress on true
    val repos: List<GitHubRepo> = emptyList(),      // starred repos list to show
    val foundReposNumber: Long = 0,                 // total number of all starred repos on GitHub
    val currentPage: Int = 1,                       // number of current shown page
    val maxPage: Int = 1,                           // maximum page number we can request from GitHub
    val reposPerPage: Int = 1,                      // maximum repos per page (in a list)
    val error: ErrorState = ErrorState.NO_ERROR,
)
