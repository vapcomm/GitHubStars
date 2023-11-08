/*
 * (c) VAP Communications Group, 2023
 */

package online.vapcom.githubstars.ui.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import online.vapcom.githubstars.data.ErrorState
import online.vapcom.githubstars.data.GitHubRepo
import online.vapcom.githubstars.data.Reply
import online.vapcom.githubstars.repo.GitHubRepository

private const val TAG = "RDetailsVM"

class RepoDetailsViewModel(repoID: Long, private val ghRepository: GitHubRepository) : ViewModel() {
    private val _state = MutableStateFlow(RepoDetailsState())
    val state: StateFlow<RepoDetailsState>
        get() = _state

    init {
        Log.d(TAG, "++++ init: repoID: $repoID")
        loadRepoDetails(repoID)
    }

    private fun loadRepoDetails(repoID: Long) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = ErrorState.NO_ERROR) }
            Log.i(TAG, "++++ loadRepoDetails: START, repoID: $repoID")

            when (val reply = ghRepository.getRepoDetails(repoID)) {
                is Reply.Success -> {
                    Log.i(TAG, "++++ loadRepoDetails: loaded repo: ${reply.value}")
                    _state.update { it.copy(isLoading = false, repo = reply.value) }
                }
                is Reply.Error -> {
                    Log.i(TAG, "++++ loadRepoDetails: error: ${reply.error}")
                    _state.update { it.copy(isLoading = false, repo = GitHubRepo.EMPTY, error = reply.error) }
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

}
