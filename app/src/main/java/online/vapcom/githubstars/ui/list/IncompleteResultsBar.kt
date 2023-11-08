/*
 * (c) VAP Communications Group, 2023
 */

package online.vapcom.githubstars.ui.list

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import online.vapcom.githubstars.R
import online.vapcom.githubstars.ui.icons.Attention
import online.vapcom.githubstars.ui.icons.Refresh
import online.vapcom.githubstars.ui.theme.GitHubStarsTheme

/**
 * Shows warning about incomplete search results with reload button
 */
@Composable
fun IncompleteResultsBar(onReload: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.errorContainer)
            .padding(start = 16.dp, top = 16.dp, bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Attention,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = MaterialTheme.colorScheme.error
        )
        Text(
            text = stringResource(id = R.string.incomplete_results),
            color = MaterialTheme.colorScheme.onErrorContainer,
            modifier = Modifier.padding(start = 16.dp).weight(32f),
            style = MaterialTheme.typography.bodyMedium,
            minLines = 2,
            softWrap = true
        )

        Spacer(modifier = Modifier.weight(1f).height(1.dp))

        IconButton(onClick = onReload, modifier = Modifier.padding(start = 8.dp)) {
            Icon(
                imageVector = Refresh,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.onErrorContainer
            )
        }

    }

}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Incomplete Light")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Incomplete Dark")
@Composable
fun IncompleteResultsBarPreview() {
    GitHubStarsTheme {
        IncompleteResultsBar(
            {}
        )
    }
}
