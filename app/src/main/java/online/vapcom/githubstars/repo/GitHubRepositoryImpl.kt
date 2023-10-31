/*
 * (c) VAP Communications Group, 2023
 */

package online.vapcom.githubstars.repo

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import online.vapcom.githubstars.data.GitHubRepo
import online.vapcom.githubstars.data.Reply
import online.vapcom.githubstars.network.GitHubEndpoint

/**
 * Data source from GitHub
 */
class GitHubRepositoryImpl(private val ghEndpoint: GitHubEndpoint) : GitHubRepository {
    companion object {
        private const val TAG = "GitHubRepo"
    }

    override suspend fun getStarredReposList(): Reply<List<GitHubRepo>> = withContext(Dispatchers.IO)  {
        Log.i(TAG, ">>> getStarredReposList:")

        Reply.Success(emptyList())
    }

}
