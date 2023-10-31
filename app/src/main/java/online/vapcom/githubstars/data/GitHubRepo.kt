/*
 * (c) VAP Communications Group, 2023
 */

package online.vapcom.githubstars.data

/**
 * Description of GitHub repository
 */
data class GitHubRepo(
    val id: String,
    val name: String,       // given name
    val desc: String,       // repo description
    val stars: Long,        // number of stars
    val forks: Long,        // number of this repo forks
    val lang: String,       // primary language
    val url: String,        // URL of repo on GitHub
    val iconURL: String     // URL of repo's icon picture
)
