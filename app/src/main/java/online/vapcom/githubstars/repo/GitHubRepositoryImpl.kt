/*
 * (c) VAP Communications Group, 2023
 */

package online.vapcom.githubstars.repo

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

    private var currentPage: Int = 1
    private var reposPerPage: Int = 1

    // current shown GitHub repos list
    private var reposList: List<GitHubRepo> = emptyList()

    /**
     * Set number of repos shown on page (in repos list)
     */
    override fun setReposPerPage(reposPerPage: Int) {
        this.reposPerPage = reposPerPage
    }

    /**
     * Request repositories list from GitHub
     */
    override suspend fun getStarredReposList(): Reply<SearchReplyData> = withContext(Dispatchers.IO) {
        when (val reply = endpoint.getStarredReposList(currentPage, reposPerPage)) {
            is Reply.Success -> {
                reposList = reply.value.repos
                Reply.Success(
                    SearchReplyData(
                        totalFound = reply.value.totalCount,
                        incompleteResults = reply.value.incompleteResults,
                        currentPage = currentPage,
                        maxPage = endpoint.getMaxPage(reposPerPage),
                        reposPerPage = reposPerPage,
                        repos = reply.value.repos
                    )
                )
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

    /**
     * Switch to the previous page
     */
    override fun previousPage() {
        if(currentPage > 1)
            currentPage--
    }

    /**
     * Switch to the next page
     */
    override fun nextPage() {
        if(currentPage < endpoint.getMaxPage(reposPerPage))
            currentPage++
    }

    override fun firstPage() {
        currentPage = 1
    }

    override fun lastPage() {
        currentPage = endpoint.getMaxPage(reposPerPage)
    }

}
