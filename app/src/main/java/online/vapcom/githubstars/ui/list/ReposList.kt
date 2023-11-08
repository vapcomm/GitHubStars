/*
 * (c) VAP Communications Group, 2023
 */

package online.vapcom.githubstars.ui.list

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import online.vapcom.githubstars.R
import online.vapcom.githubstars.data.GitHubRepo
import online.vapcom.githubstars.ui.icons.Star
import online.vapcom.githubstars.ui.theme.GitHubStarsTheme
import online.vapcom.githubstars.utils.starsToShortString

/**
 * List of repo with pages navigation
 */
@Composable
fun ReposList(
    incompleteResults: Boolean,
    repos: List<GitHubRepo>,
    foundReposNumber: Long,
    currentPage: Int,
    maxPage: Int,
    itemsPerPage: Int,
    onPage: (direction: PageDirection) -> Unit,
    onRepoClick: (repoID: Long) -> Unit,
    onReload: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        if (incompleteResults) {
            item(key = Long.MIN_VALUE) {
                IncompleteResultsBar(onReload = onReload)
                Divider(modifier = Modifier.fillMaxWidth())
            }
        }

        if (repos.isNotEmpty()) {
            item(key = Long.MIN_VALUE + 1) {
                PagesNavigationBar(
                    foundReposNumber, currentPage, maxPage, itemsPerPage,
                    onPage = onPage,
                    modifier = Modifier.padding(start = 14.dp)
                )
                Divider(modifier = Modifier.fillMaxWidth())
            }
        }

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

        if (repos.isNotEmpty()) {
            item(key = Long.MAX_VALUE) {
                Divider(modifier = Modifier.fillMaxWidth())
                PagesNavigationBar(
                    foundReposNumber, currentPage, maxPage, itemsPerPage,
                    onPage = onPage,
                    modifier = Modifier.padding(start = 14.dp)
                )
            }
        }
    }

} // ReposList

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "List Light")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "List Dark")
@Composable
fun ReposListBodyPreview() {
    GitHubStarsTheme {
        ReposList(
            incompleteResults = true,
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
            750000, 1, 20, 50,
            {}, {}, {}
        )
    }
}
