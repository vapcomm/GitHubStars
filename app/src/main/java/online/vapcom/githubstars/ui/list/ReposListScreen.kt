/*
 * (c) VAP Communications Group, 2023
 */

package online.vapcom.githubstars.ui.list

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import online.vapcom.githubstars.R
import online.vapcom.githubstars.data.GitHubRepo
import online.vapcom.githubstars.data.UIErrno
import online.vapcom.githubstars.ui.common.ScreenWithErrorBottomSheet
import online.vapcom.githubstars.ui.theme.GitHubStarsTheme

/**
 * List of GitHub repositories
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReposListScreen(
    viewModel: RepoListViewModel,
    onRepoClick: (repoID: Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsState()

    ScreenWithErrorBottomSheet(
        isError = state.error.isError() && state.error.code != UIErrno.DATA_ERROR.errno,
        error = state.error,
        clearErrorState = viewModel::clearErrorState
    ) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(text = stringResource(id = R.string.repos_list), maxLines = 1, overflow = TextOverflow.Ellipsis)
                    }
                )
            },
            modifier = modifier
        ) { innerPadding ->
            when (state.error.code) {
                UIErrno.DATA_ERROR.errno -> {
                    Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                        UnableToLoadData(
                            onReload = viewModel::reload,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }

                else -> ReposListBody(
                    isLoading = state.isLoading,
                    repos = state.repos,
                    foundReposNumber = state.foundReposNumber,
                    currentPage = state.currentPage,
                    maxPage = state.maxPage,
                    reposPerPage = state.reposPerPage,
                    onPreviousPage = viewModel::previousPage,
                    onNextPage = viewModel::nextPage,
                    onRepoClick = onRepoClick,
                    onReload = viewModel::reload,
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}

@Composable
fun ReposListBody(
    isLoading: Boolean,
    repos: List<GitHubRepo>,
    foundReposNumber: Long,
    currentPage: Int,
    maxPage: Int,
    reposPerPage: Int,
    onPreviousPage: () -> Unit,
    onNextPage: () -> Unit,
    onRepoClick: (repoID: Long) -> Unit,
    onReload: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        if (repos.isEmpty() && !isLoading) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.align(Alignment.Center)
            ) {
                Text(
                    text = stringResource(R.string.repositories_not_found),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
                )
                Button(
                    onClick = onReload,
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text(text = stringResource(R.string.reload))
                }
            }
        } else {
            if (!isLoading)
                ReposList(
                    repos, foundReposNumber, currentPage, maxPage, reposPerPage,
                    onPreviousPage, onNextPage, onRepoClick
                )
        }

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .height(48.dp)
                    .width(48.dp)
                    .align(Alignment.Center),
                color = MaterialTheme.colorScheme.primary
            )
        }

    } // Box
}


@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Empty List Light")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Empty List Dark")
@Composable
fun ReposListBodyEmptyPreview() {
    GitHubStarsTheme {
        ReposListBody(
            isLoading = false,
            repos = emptyList(),
            foundReposNumber = 230000,
            currentPage = 1,
            maxPage = 20,
            reposPerPage = 50,
            {}, {}, {}, {}
        )
    }
}
