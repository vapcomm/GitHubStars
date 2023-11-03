/*
 * (c) VAP Communications Group, 2023
 */

package online.vapcom.githubstars.ui

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import online.vapcom.githubstars.ui.details.RepoDetailsScreen
import online.vapcom.githubstars.ui.details.RepoDetailsViewModel
import online.vapcom.githubstars.ui.list.RepoListViewModel
import online.vapcom.githubstars.ui.list.ReposListScreen
import online.vapcom.githubstars.ui.theme.GitHubStarsTheme
import org.koin.androidx.compose.getViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinContext
import org.koin.core.parameter.parametersOf

@Composable
fun GitHubStarsApp() {
    KoinContext {
        GitHubStarsTheme {
            val navController = rememberNavController()
            TopNavigation(navController)
        }
    }
}

private const val TAG = "TopNavHost"

/**
 * Simple top level navigation container
 */
@Composable
fun TopNavigation(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = "repos"
    ) {
        // repositories list
        composable("repos") {
            BackHandler(true) {
                // NOTE: user can't go back from top screen
                Log.w(TAG, "~~ repos: system Back button blocked")
            }

            val viewModel = getViewModel<RepoListViewModel>()

            ReposListScreen(
                viewModel = viewModel,
                onRepoClick = { repoID ->
                    navController.navigate("details/$repoID")
                },
                onReload = viewModel::reload
            )
        }

        // repo details
        composable(
            route = "details/{repoID}",
            arguments = listOf(navArgument("repoID") { type = NavType.LongType })
        ) { entry ->
            val repoID = entry.arguments?.getLong("repoID") ?: 0
            val viewModel: RepoDetailsViewModel = koinViewModel(parameters = { parametersOf(repoID) })

            val context = LocalContext.current
            RepoDetailsScreen(
                viewModel = viewModel,
                onUpClick = { navController.popBackStack() },
                onLinkClick = { url ->
                    Log.w(TAG, "~~ details: start browser on '$url'")
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(url)
                    context.startActivity(intent, null)
                }
            )
        }
    }
}
