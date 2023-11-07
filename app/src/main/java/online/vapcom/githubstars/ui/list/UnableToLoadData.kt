/*
 * (c) VAP Communications Group, 2023
 */

package online.vapcom.githubstars.ui.list

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import online.vapcom.githubstars.R
import online.vapcom.githubstars.ui.theme.GitHubStarsTheme

@Composable
fun UnableToLoadData(onReload: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        //TODO: error picture

        Text(
            text = stringResource(R.string.unable_to_load_data),
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

}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Unable Light")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Unable Dark")
@Composable
fun UnableToLoadDataPreview() {
    GitHubStarsTheme {
        UnableToLoadData(onReload = {})
    }
}
