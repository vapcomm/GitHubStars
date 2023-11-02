/*
 * (c) VAP Communications Group, 2023
 */

package online.vapcom.githubstars.ui.common

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import online.vapcom.githubstars.R
import online.vapcom.githubstars.data.ErrorState
import online.vapcom.githubstars.ui.theme.GitHubStarsTheme

/**
 * Show common error
 */
@Composable
fun CommonErrorBody(firstMessage: String, error: ErrorState, whatToDoMessage: String, modifier: Modifier = Modifier) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        item {
            Row {
                Image(
                    painter = painterResource(R.drawable.ic_error),
                    contentDescription = null,
                    modifier = Modifier.size(48.dp)
                )
                Text(
                    text = stringResource(R.string.request_error),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .align(alignment = Alignment.CenterVertically)
                )
            }
        }

        item {
            if (error.code != 0 || error.desc.isNotBlank()) {
                Text(
                    text = stringResource(R.string.error_description, error.code, error.desc),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
            // TODO: more/less button to show error.exMessage

            Text(
                text = if (firstMessage.isNotBlank()) firstMessage else stringResource(R.string.try_again),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .padding(top = 16.dp)
            )

            Text(
                text = whatToDoMessage,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .padding(top = 8.dp)
            )
        }

        // TODO: Report error button
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "SignIn Light")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "SignIn Dark")
@Composable
fun CommonErrorBodyPreview() {
    GitHubStarsTheme {
        CommonErrorBody(
            "Sometimes it happens, sometimes it's gone",
            ErrorState(1011, "Something happened to me", "Something I can't control"),
            "Try to listen to Infected Mushrooms"
        )
    }
}
