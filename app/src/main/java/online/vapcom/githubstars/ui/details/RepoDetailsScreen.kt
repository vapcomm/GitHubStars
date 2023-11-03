/*
 * (c) VAP Communications Group, 2023
 */

package online.vapcom.githubstars.ui.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import online.vapcom.githubstars.R
import online.vapcom.githubstars.data.GitHubRepo
import online.vapcom.githubstars.ui.common.ScreenWithErrorBottomSheet
import online.vapcom.githubstars.ui.icons.ArrowBack

/**
 * Show GitHub repository details
 */
@Composable
fun RepoDetailsScreen(
    viewModel: RepoDetailsViewModel,
    onUpClick: () -> Unit,
    onLinkClick: (url: String) -> Unit,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsState()

    ScreenWithErrorBottomSheet(
        isError = state.error.isError(),
        error = state.error,
        clearErrorState = viewModel::clearErrorState
    ) {
        RepoDetailsBody(
            isLoading = state.isLoading,
            repo = state.repo,
            onUpClick = onUpClick,
            onLinkClick = onLinkClick,
            modifier = modifier
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepoDetailsBody(
    isLoading: Boolean,
    repo: GitHubRepo,
    onUpClick: () -> Unit,
    onLinkClick: (url: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onUpClick) {
                        Icon(
                            imageVector = ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                },
                title = { Text(text = repo.name, maxLines = 1, overflow = TextOverflow.Ellipsis) }
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .height(48.dp)
                        .width(48.dp)
                        .align(Alignment.Center),
                    color = MaterialTheme.colorScheme.primary
                )
            } else {
                RepoDetails(repo, onLinkClick)
            }

        } // Box
    }

}
