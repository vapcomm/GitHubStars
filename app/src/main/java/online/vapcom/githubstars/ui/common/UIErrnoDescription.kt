/*
 * (c) VAP Communications Group, 2023
 */

package online.vapcom.githubstars.ui.common

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import online.vapcom.githubstars.R
import online.vapcom.githubstars.data.ErrorState
import online.vapcom.githubstars.data.UIErrno
import online.vapcom.githubstars.ui.theme.GitHubStarsTheme

/**
 * Bottom sheet content for UI processable errors
 */
@Composable
fun UIErrnoDescription(error: ErrorState, modifier: Modifier = Modifier) {
    when (error.code) {
        UIErrno.CONNECTION_ERROR.errno -> NetworkErrorBody(
            stringResource(R.string.connection_error),
            error.desc,
            stringResource(R.string.check_network_connection),
            modifier
        )

        UIErrno.CLIENT_ERROR.errno -> NetworkErrorBody(
            stringResource(R.string.client_problem),
            error.desc,
            stringResource(R.string.retry_or_update),
            modifier
        )

        UIErrno.SERVER_ERROR.errno -> NetworkErrorBody(
            stringResource(R.string.server_side_problem),
            error.desc,
            stringResource(R.string.our_problem),
            modifier
        )

        UIErrno.TIMEOUT.errno -> NetworkErrorBody(
            stringResource(R.string.no_server_response),
            error.desc,
            stringResource(R.string.network_problem_try_later),
            modifier
        )

        UIErrno.INTERNAL_ERROR.errno -> NetworkErrorBody(
            stringResource(R.string.client_problem),
            error.desc,
            stringResource(R.string.try_again),
            modifier
        )

        UIErrno.CONTENT_ERROR.errno -> NetworkErrorBody(
            stringResource(R.string.server_side_problem),
            error.desc,
            stringResource(R.string.try_again),
            modifier
        )

        else -> CommonErrorBody(
            stringResource(R.string.try_again),
            error,
            stringResource(R.string.if_error_repeats_call_support),
            modifier
        )
    }
}

@Composable
fun NetworkErrorBody(
    firstMessage: String,
    desc: String,
    whatToDoMessage: String,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.surface),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = stringResource(R.string.error),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.error
            )
        }
        item {
            Text(
                text = firstMessage,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        item {
            if (desc.isNotBlank()) {
                Text(
                    text = desc,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
        item {
            Text(
                text = whatToDoMessage,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}


@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Network Light")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Network Dark")
@Composable
fun UIEServerPreview() {
    GitHubStarsTheme {
        UIErrnoDescription(
            ErrorState(UIErrno.SERVER_ERROR.errno, "501 server has big problems with long description")
        )
    }
}
