/*
 * (c) VAP Communications Group, 2023
 */

package online.vapcom.githubstars.ui.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier

/**
 * List of GitHub repositories
 */
@Composable
fun ReposListScreen(
    viewModel: RepoListViewModel,
    onRepoClick: (repoId: String) -> Unit,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsState()

}
