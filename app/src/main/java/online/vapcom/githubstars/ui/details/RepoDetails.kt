/*
 * (c) VAP Communications Group, 2023
 */

package online.vapcom.githubstars.ui.details

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import online.vapcom.githubstars.ui.icons.Fork
import online.vapcom.githubstars.ui.icons.Star
import online.vapcom.githubstars.ui.theme.GitHubStarsTheme
import online.vapcom.githubstars.utils.starsToShortString

@Composable
fun RepoDetails(repo: GitHubRepo, onLinkClick: (url: String) -> Unit, modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        // owner's avatar and full repo name
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = repo.iconURL,
                contentDescription = null,
                placeholder = painterResource(R.drawable.icon_placeholder),
                modifier = Modifier
                    .padding(top = 6.dp)
                    .size(40.dp)
                    .clip(RoundedCornerShape(4.dp))
            )
            Text(
                text = repo.name,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        // description
        Text(
            text = repo.desc,
            modifier = Modifier.padding(top = 16.dp),
            overflow = TextOverflow.Ellipsis,
            maxLines = 200,
            style = MaterialTheme.typography.bodyMedium
        )

        // stars and forks
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 16.dp)
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

            Spacer(modifier = Modifier.width(24.dp))

            Icon(
                imageVector = Fork,
                contentDescription = null,
                modifier = Modifier.size(12.dp)
            )
            Text(
                text = starsToShortString(repo.forks),
                maxLines = 1,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        // language
        Text(
            text = repo.lang,
            modifier = Modifier.padding(top = 8.dp),
            style = MaterialTheme.typography.bodyMedium
        )

        OutlinedButton(
            onClick = { onLinkClick(repo.url) },
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 56.dp)
        ) {
            Text(
                text = stringResource(id = R.string.see_on_github),
                style = MaterialTheme.typography.labelLarge
            )
        }

    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Details Light")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Details Dark")
@Composable
fun RepoDetailsPreview() {
    GitHubStarsTheme {
        RepoDetails(
            repo = GitHubRepo(
                1, "vapcom/picocbor", "Minimalistic approach to CBOR encoding in pure Kotlin",
                999500, 0, "Kotlin", "https://github.com/vapcomm/picocbor", "https://localhost/icon.png"
            ),
            onLinkClick = {}
        )
    }
}

