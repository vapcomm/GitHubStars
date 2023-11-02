/*
 * (c) VAP Communications Group, 2023
 */

package online.vapcom.githubstars.ui

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import online.vapcom.githubstars.ui.list.RepoListViewModel
import online.vapcom.githubstars.ui.list.ReposListScreen
import online.vapcom.githubstars.ui.theme.GitHubStarsTheme
import org.koin.androidx.compose.getViewModel
import org.koin.compose.KoinContext

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
        /*TODO
                composable(
                    route = "meaning/{meaningID}",
                    arguments = listOf(navArgument("meaningID") { type = NavType.StringType })
                ) { entry ->
                    val meaningID = entry.arguments?.getString("meaningID") ?: ""
                    val viewModel = getViewModel<MeaningViewModel>(parameters = { parametersOf(meaningID) })

                    MeaningScreen(
                        viewModel = viewModel,
                        onUpClick = { navController.popBackStack() }
                    )
                }

         */
    }
}
