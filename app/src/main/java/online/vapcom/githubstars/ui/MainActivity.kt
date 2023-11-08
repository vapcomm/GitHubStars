/*
 * (c) VAP Communications Group, 2023
 */

package online.vapcom.githubstars.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GitHubStarsApp()
        }
    }
}
