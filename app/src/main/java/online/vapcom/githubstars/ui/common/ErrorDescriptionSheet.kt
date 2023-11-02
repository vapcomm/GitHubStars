/*
 * (c) VAP Communications Group, 2023
 */

package online.vapcom.githubstars.ui.common

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
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
 * Bottom sheet content to show error code and description
 */
@Composable
fun ErrorDescriptionSheet(isError: Boolean, error: ErrorState) {
    if (isError) {
        when {
            error.isUIErrno() -> UIErrnoDescription(error)
            error.isFatal() -> FatalErrorDescription(error, modifier = Modifier.padding(16.dp))
            else -> CommonErrorBody(
                firstMessage = stringResource(R.string.try_again),
                error = error,
                whatToDoMessage = stringResource(R.string.if_error_repeats_call_support),
                modifier = Modifier.padding(16.dp)
            )
        }
    } else {
        // NOTE: without this it will be java.lang.IllegalArgumentException: The initial value must have an associated anchor.
        // Solution: put any visible composable with minimum height.
        // https://stackoverflow.com/questions/68623965/jetpack-compose-modalbottomsheetlayout-throws-java-lang-illegalargumentexception
        Spacer(modifier = Modifier.height(1.dp))
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Server Light")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Server Dark")
@Composable
fun EDSServerPreview() {
    GitHubStarsTheme {
        ErrorDescriptionSheet(
            true,
            ErrorState(UIErrno.SERVER_ERROR.errno, "501 server has big problems with long description")
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "UIErrno Light")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "UIErrno Dark")
@Composable
fun UIErrnoPreview() {
    GitHubStarsTheme {
        Column {
            ErrorDescriptionSheet(
                true,
                ErrorState(UIErrno.CLIENT_ERROR.errno, "Update app")
            )
            Divider()

            ErrorDescriptionSheet(
                true,
                ErrorState(UIErrno.TIMEOUT.errno, "request timeout")
            )
            Divider()

            ErrorDescriptionSheet(
                true,
                ErrorState(UIErrno.SESSION_CLOSED.errno, "Need to relogin")
            )
            Divider()

            ErrorDescriptionSheet(
                true,
                ErrorState(UIErrno.INTERNAL_ERROR.errno, "5012 unable to write file")
            )
            Divider()

            ErrorDescriptionSheet(
                true,
                ErrorState(UIErrno.CONTENT_ERROR.errno, "4010 empty thumbnail")
            )
            Divider()
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Fatal Light")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Fatal Dark")
@Composable
fun EDSFatalPreview() {
    GitHubStarsTheme {
        ErrorDescriptionSheet(
            true,
            ErrorState(1, "User storage corrupted", "IO exception")
        )
    }
}
