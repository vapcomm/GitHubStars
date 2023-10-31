/*
 * (c) VAP Communications Group, 2023
 */

package online.vapcom.githubstars.repo

import online.vapcom.githubstars.data.GitHubRepo
import online.vapcom.githubstars.data.Reply

interface GitHubRepository {
    suspend fun getStarredReposList(): Reply<List<GitHubRepo>>

}
