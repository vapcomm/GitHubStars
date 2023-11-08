/*
 * (c) VAP Communications Group, 2023
 */

package online.vapcom.githubstars.repo

import BaseRepositoryTest
import online.vapcom.githubstars.data.Reply
import online.vapcom.githubstars.data.UIErrno
import online.vapcom.githubstars.network.GitHubEndpoint
import online.vapcom.githubstars.network.makeGitHubMockEngine
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ReposListTest : BaseRepositoryTest(makeGitHubMockEngine()) {

    private val ghEndpoint = GitHubEndpoint("https://api/search", httpClient)

    @Test
    fun completeResponse() = runRepoTest {
        val ghRepository = GitHubRepositoryImpl(ghEndpoint)

        ghRepository.setReposPerPage(50)
        val reply = ghRepository.getStarredReposList()
        assertTrue(reply is Reply.Success)

        val data = reply.value
        // data from first page due page has not been changed yet
        assertEquals(6065596, data.totalFound)
        assertEquals(false, data.incompleteResults)
        assertEquals(1, data.currentPage)
        assertEquals(20, data.maxPage)
        assertEquals(50, data.reposPerPage)

        assertEquals(1, data.repos.size)
        val repo = data.repos[0]

        assertEquals(2126244, repo.id)
        assertEquals("twbs/bootstrap", repo.name)
        assertEquals("The most popular HTML, CSS, and JavaScript framework for developing responsive, mobile first projects on the web.", repo.desc)
        assertEquals(165809, repo.stars)
        assertEquals(79023, repo.forks)
        assertEquals("JavaScript", repo.lang)
        assertEquals("https://github.com/twbs/bootstrap", repo.url)
        assertEquals("https://avatars.githubusercontent.com/u/2918581?v=4", repo.iconURL)
    }

    @Test
    fun incompleteResponse() = runRepoTest {
        val ghRepository = GitHubRepositoryImpl(ghEndpoint)

        ghRepository.setReposPerPage(50)
        ghRepository.nextPage() // page 2
        val reply = ghRepository.getStarredReposList()
        assertTrue(reply is Reply.Success)

        val data = reply.value
        assertEquals(5979794, data.totalFound)
        assertEquals(true, data.incompleteResults)
        assertEquals(2, data.currentPage)
        assertEquals(20, data.maxPage)
        assertEquals(50, data.reposPerPage)

        assertEquals(1, data.repos.size)
        val repo = data.repos[0]

        assertEquals(177736533, repo.id)
        assertEquals("996icu/996.ICU", repo.name)
        assertEquals("Repo for counting stars and contributing. Press F to pay respect to glorious developers.", repo.desc)
        assertEquals(268127, repo.stars)
        assertEquals(21479, repo.forks)
        assertEquals("", repo.lang) //NOTE: null in response
        assertEquals("https://github.com/996icu/996.ICU", repo.url)
        assertEquals("https://avatars.githubusercontent.com/u/48942249?v=4", repo.iconURL)
    }

    @Test
    fun emptyResponse() = runRepoTest {
        val ghRepository = GitHubRepositoryImpl(ghEndpoint)

        ghRepository.setReposPerPage(50)
        ghRepository.nextPage() // page 2
        ghRepository.nextPage() // page 3
        val reply = ghRepository.getStarredReposList()
        assertTrue(reply is Reply.Success)

        val data = reply.value
        // default data
        assertEquals(0, data.totalFound)
        assertEquals(false, data.incompleteResults)
        assertEquals(3, data.currentPage)
        assertEquals(20, data.maxPage)
        assertEquals(50, data.reposPerPage)

        assertEquals(0, data.repos.size)
    }

    @Test
    fun emptyDataResponse() = runRepoTest {
        val ghRepository = GitHubRepositoryImpl(ghEndpoint)

        ghRepository.setReposPerPage(50)
        ghRepository.nextPage() // page 2
        ghRepository.nextPage() // page 3
        ghRepository.nextPage() // page 4
        val reply = ghRepository.getStarredReposList()
        assertTrue(reply is Reply.Success)

        val data = reply.value
        // default data
        assertEquals(0, data.totalFound)
        assertEquals(false, data.incompleteResults)
        assertEquals(4, data.currentPage)
        assertEquals(20, data.maxPage)
        assertEquals(50, data.reposPerPage)

        assertEquals(0, data.repos.size)
    }

    @Test
    fun error422() = runRepoTest {
        val ghRepository = GitHubRepositoryImpl(ghEndpoint)

        ghRepository.setReposPerPage(50)
        ghRepository.lastPage()
        val reply = ghRepository.getStarredReposList()
        assertTrue(reply is Reply.Error)

        assertEquals(UIErrno.CONTENT_ERROR.errno, reply.error.code)
        assertEquals("Only the first 1000 search results are available", reply.error.desc)
    }

}
