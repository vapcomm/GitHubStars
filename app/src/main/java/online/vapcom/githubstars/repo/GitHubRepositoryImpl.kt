/*
 * (c) VAP Communications Group, 2023
 */

package online.vapcom.githubstars.repo

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import online.vapcom.githubstars.data.ErrorState
import online.vapcom.githubstars.data.GitHubRepo
import online.vapcom.githubstars.data.Reply
import online.vapcom.githubstars.data.UIErrno
import online.vapcom.githubstars.network.GitHubEndpoint

/**
 * Data source from GitHub
 */
class GitHubRepositoryImpl(private val endpoint: GitHubEndpoint) : GitHubRepository {
    companion object {
        private const val TAG = "GitHubRepo"
    }

    // current shown GitHub repos list
    private var reposList: List<GitHubRepo> = emptyList()

    /**
     * Request repositories list from GitHub
     */
    override suspend fun getStarredReposList(): Reply<List<GitHubRepo>> = withContext(Dispatchers.IO)  {
        Log.i(TAG, ">>> getStarredReposList:")
        when (val reply = endpoint.getStarredReposList()) {
            is Reply.Success -> {
                reposList = reply.value
                reply
            }
            is Reply.Error -> reply
        }
    }

    /**
     * Return GitHub repository details by its ID.
     * Should be called after getStarredReposList().
     */
    override suspend fun getRepoDetails(repoID: Long): Reply<GitHubRepo> {
        val repo = reposList.find { it.id == repoID }
        return if (repo != null) Reply.Success(repo)
        else Reply.Error(ErrorState(UIErrno.DATA_NOT_FOUND.errno, "Repository with ID '$repoID' not found"))
    }

}
