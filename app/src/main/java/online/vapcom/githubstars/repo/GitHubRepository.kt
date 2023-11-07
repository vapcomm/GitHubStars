/*
 * (c) VAP Communications Group, 2023
 */

package online.vapcom.githubstars.repo

import online.vapcom.githubstars.data.GitHubRepo
import online.vapcom.githubstars.data.Reply

interface GitHubRepository {
    suspend fun getStarredReposList(): Reply<SearchReplyData>
    suspend fun getRepoDetails(repoID: Long): Reply<GitHubRepo>
    fun previousPage()
    fun nextPage()
    fun setReposPerPage(reposPerPage: Int)
}
