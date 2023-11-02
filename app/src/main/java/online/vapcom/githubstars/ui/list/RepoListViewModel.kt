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

private const val TAG = "RepoListVM"

class RepoListViewModel(private val ghRepository: GitHubRepository) : ViewModel() {

    private val _state = MutableStateFlow(RepoListState())
    val state: StateFlow<RepoListState>
        get() = _state

    init {
        // read first page of repositories list
        loadRepositoriesList()
    }

    private fun loadRepositoriesList() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = ErrorState.NO_ERROR) }
            Log.i(TAG, "++++ loadRepositoriesList: START")

            when (val reply = ghRepository.getStarredReposList()) {
                is Reply.Success -> {
                    Log.i(TAG, "++++ loadRepositoriesList: loaded repos: ${reply.value.size}")
                    _state.update { it.copy(isLoading = false, repos = reply.value) }
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

}
