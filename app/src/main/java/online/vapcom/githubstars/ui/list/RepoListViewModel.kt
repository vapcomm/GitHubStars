/*
 * (c) VAP Communications Group, 2023
 */

package online.vapcom.githubstars.ui.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import online.vapcom.githubstars.data.ErrorState
import online.vapcom.githubstars.data.Reply
import online.vapcom.githubstars.repo.GitHubRepository

class RepoListViewModel(private val ghRepository: GitHubRepository) : ViewModel() {

    companion object {
        private const val TAG = "RepoListVM"
        private const val ITEMS_PER_PAGE = 25   // size of the one scrolled page of repos list
    }

    private val _state = MutableStateFlow(RepoListState(reposPerPage = ITEMS_PER_PAGE))
    val state: StateFlow<RepoListState>
        get() = _state

    init {
        ghRepository.setReposPerPage(ITEMS_PER_PAGE)
        // read first page of repositories list
        loadRepositoriesList()
    }

    private fun loadRepositoriesList() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = ErrorState.NO_ERROR) }
            Log.i(TAG, "++++ loadRepositoriesList: START")

            when (val reply = ghRepository.getStarredReposList()) {
                is Reply.Success -> {
                    Log.i(TAG, "++++ loadRepositoriesList: loaded repos: ${reply.value.repos.size}")
                    _state.update {
                        it.copy(
                            isLoading = false,
                            repos = reply.value.repos,
                            foundReposNumber = reply.value.totalFound,
                            currentPage = reply.value.currentPage,
                            maxPage = reply.value.maxPage,
                            reposPerPage = reply.value.reposPerPage
                        )
                    }
                }

                is Reply.Error -> {
                    Log.i(TAG, "++++ loadRepositoriesList: error: ${reply.error}")
                    _state.update { it.copy(isLoading = false, repos = emptyList(), error = reply.error) }
                }
            }
        }
    }

    /**
     * Clear up error state
     */
    fun clearErrorState() {
        Log.i(TAG, "++++ clearErrorState")
        _state.update { it.copy(error = ErrorState.NO_ERROR) }
    }

    /**
     * Full reload of repos list
     */
    fun reload() {
        loadRepositoriesList()
    }

    /**
     * Go to the previous page and get new repos list
     */
    fun previousPage() {
        ghRepository.previousPage()
        loadRepositoriesList()
    }

    /**
     * Go to the next page and get new repos list
     */
    fun nextPage() {
        ghRepository.nextPage()
        loadRepositoriesList()
    }

}
