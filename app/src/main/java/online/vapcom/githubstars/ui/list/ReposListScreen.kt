/*
 * (c) VAP Communications Group, 2023
 */

package online.vapcom.githubstars.ui.list

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import online.vapcom.githubstars.R
import online.vapcom.githubstars.data.GitHubRepo
import online.vapcom.githubstars.ui.common.ScreenWithErrorBottomSheet
import online.vapcom.githubstars.ui.icons.Star
import online.vapcom.githubstars.ui.theme.GitHubStarsTheme
import online.vapcom.githubstars.utils.starsToShortString

/**
 * List of GitHub repositories
 */
@Composable
fun ReposListScreen(
    viewModel: RepoListViewModel,
    onRepoClick: (repoID: Long) -> Unit,
    onReload: () -> Unit,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsState()

    ScreenWithErrorBottomSheet(
        isError = state.error.isError(),
        error = state.error,
        clearErrorState = viewModel::clearErrorState
    ) {
        ReposListBody(
            isLoading = state.isLoading,
            repos = state.repos,
            onRepoClick = onRepoClick,
            onReload = onReload,
            modifier = modifier
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReposListBody(
    isLoading: Boolean,
    repos: List<GitHubRepo>,
    onRepoClick: (repoID: Long) -> Unit,
    onReload: () -> Unit,
    modifier: Modifier = Modifier
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (repos.isEmpty() && !isLoading) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    Text(
                        text = stringResource(R.string.repositories_not_found),
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
                ReposList(repos, onRepoClick)
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
}

@Composable
fun ReposList(repos: List<GitHubRepo>, onRepoClick: (repoID: Long) -> Unit) {
    LazyColumn(
        contentPadding = PaddingValues(bottom = 16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(items = repos, key = { it.id }) { repo ->
            // three-lines list element
            // https://m3.material.io/components/lists/specs#9c38a8eb-a45f-4924-ba63-352c88a0e085
            Row(
                modifier = Modifier
                    .clickable { onRepoClick(repo.id) }
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                AsyncImage(
                    model = repo.iconURL,
                    contentDescription = null,
                    placeholder = painterResource(R.drawable.icon_placeholder),
                    modifier = Modifier
                        .padding(top = 6.dp)
                        .size(40.dp)
                        .clip(RoundedCornerShape(4.dp))
                )
                Column(modifier = Modifier.padding(start = 16.dp)) {
                    Text(
                        text = repo.name,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = repo.desc,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        Icon(
                            imageVector = Star,
                            contentDescription = null,
                            modifier = Modifier.size(12.dp)
                        )
                        Text(
                            text = starsToShortString(repo.stars),
                            maxLines = 1,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        }
    }

}


@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "List Light")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "List Dark")
@Composable
fun ReposListBodyPreview() {
    GitHubStarsTheme {
        ReposList(
            repos = listOf(
                GitHubRepo(
                    1, "vapcom/picocbor", "Minimalistic approach to CBOR encoding in pure Kotlin",
                    999500, 0, "Kotlin", "", "https://localhost/icon.png"
                ),
                GitHubRepo(
                    2, "vapcom/kot1ha", "t1ha in Kotlin",
                    5, 0, "Kotlin", "", "https://localhost/icon.png"
                )
            ),
            onRepoClick = {}
        )
    }
}


@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Empty List Light")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Empty List Dark")
@Composable
fun ReposListBodyEmptyPreview() {
    GitHubStarsTheme {
        ReposListBody(
            isLoading = false,
            repos = emptyList(),
            {}, {}
        )
    }
}
