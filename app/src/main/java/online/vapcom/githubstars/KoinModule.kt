/*
 * (c) VAP Communications Group, 2023
 */

package online.vapcom.githubstars

import online.vapcom.githubstars.network.GitHubEndpoint
import online.vapcom.githubstars.repo.GitHubRepository
import online.vapcom.githubstars.repo.GitHubRepositoryImpl
import online.vapcom.githubstars.ui.list.RepoListViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

/**
 * Koin's app module with Repo and ViewModels
 */
val appModule = module {
    single {
        GitHubEndpoint(searchURL = BuildConfig.GITHUB_SEARCH_ENDPOINT)
    }

    singleOf(::GitHubRepositoryImpl) { bind<GitHubRepository>() }

    viewModelOf(::RepoListViewModel)
    //TODO: viewModelOf(::RepoDetailsViewModel)
}
