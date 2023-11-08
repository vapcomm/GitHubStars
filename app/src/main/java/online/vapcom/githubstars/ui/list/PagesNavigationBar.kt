/*
 * (c) VAP Communications Group, 2023
 */

package online.vapcom.githubstars.ui.list

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import online.vapcom.githubstars.R
import online.vapcom.githubstars.ui.icons.ArrowLeft
import online.vapcom.githubstars.ui.icons.ArrowRight
import online.vapcom.githubstars.ui.icons.Repo
import online.vapcom.githubstars.ui.theme.GitHubStarsTheme
import online.vapcom.githubstars.utils.starsToShortString

/**
 * Top/bottom bar to switch pages on repos list
 */
@Composable
fun PagesNavigationBar(
    foundReposNumber: Long,
    currentPage: Int,
    maxPage: Int,
    itemsPerPage: Int,
    onPage: (direction: PageDirection) -> Unit,
    modifier: Modifier = Modifier
) {
    var isMenuDisplayed by remember { mutableStateOf(false) }

    Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Repo,
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = starsToShortString(foundReposNumber),
            maxLines = 1,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(start = 8.dp, end = 4.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        val pagesText = "${(currentPage - 1) * itemsPerPage + 1}-${currentPage * itemsPerPage}/${maxPage * itemsPerPage}"

        if (currentPage > 1) {
            IconButton(onClick = { onPage(PageDirection.PREVIOUS) }, modifier = Modifier.padding(start = 8.dp)) {
                Icon(
                    imageVector = ArrowLeft,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        Box(modifier = Modifier.padding(end = if (currentPage >= maxPage) 16.dp else 0.dp)) {
            TextButton(
                onClick = { isMenuDisplayed = !isMenuDisplayed },
                contentPadding = PaddingValues(
                    start = 4.dp,
                    top = ButtonDefaults.ContentPadding.calculateTopPadding(),
                    end = 4.dp,
                    bottom = ButtonDefaults.ContentPadding.calculateBottomPadding()
                )
            ) {
                Text(text = pagesText, textAlign = TextAlign.Center, style = MaterialTheme.typography.labelLarge)
            }

            DropdownMenu(
                expanded = isMenuDisplayed,
                onDismissRequest = { isMenuDisplayed = false }
            ) {
                DropdownMenuItem(
                    text = { Text(text = stringResource(id = R.string.first_page)) },
                    onClick = {
                        isMenuDisplayed = false
                        onPage(PageDirection.FIRST)
                    },
                )
                DropdownMenuItem(
                    text = { Text(text = stringResource(id = R.string.last_page)) },
                    onClick = {
                        isMenuDisplayed = false
                        onPage(PageDirection.LAST)
                    },
                )
            }
        }

        if (currentPage < maxPage) {
            IconButton(onClick = { onPage(PageDirection.NEXT) }) {
                Icon(
                    imageVector = ArrowRight,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Pages Light")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Pages Dark")
@Composable
fun RepoDetailsPreview() {
    GitHubStarsTheme {
        PagesNavigationBar(
            foundReposNumber = 8000000,
            currentPage = 2,
            maxPage = 20,
            itemsPerPage = 50,
            {}
        )
    }
}
