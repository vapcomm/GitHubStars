/*
 * (c) VAP Communications Group, 2023
 */

package online.vapcom.githubstars.data

/**
 * Common reply used in repositories and use cases or whole project
 */
sealed class Reply<out T : Any> {
    data class Success<out T : Any>(val value: T) : Reply<T>()
    data class Error(val error: ErrorState) : Reply<Nothing>()
}
