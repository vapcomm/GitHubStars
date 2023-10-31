/**
 * (c) VAP Communications Group, 2023
 */

package online.vapcom.githubstars.data

/**
 * Error state description used in low levels' replies to process and/or show error on UI level
 */
data class ErrorState(
    val code: Int,              // error code, described below
    val desc: String,           // error description suitable for user or tech support
    val exMessage: String = "", // Exception message (only for tech support)
    val stackTrace: String = "" // exception's stack trace (only for tech support)
) {
    companion object {
        val NO_ERROR = ErrorState(0, "")
    }

    fun isError() = code != 0

    /**
     * Check for fatal error severity level
     */
    fun isFatal() = code in 1..99

    /**
     * Check for errors suitable to show to user in UI
     */
    fun isUIErrno() = code < 0

    /**
     * Check for any other errors
     */
    fun isCommon() = code >= 100
}

/*
    How to encode error code:
    Zero - no error
    1..99 - fatal errors, app is unable to continue any work, show dialog "Reinstall app or ask support"
    Other positive values - system errors with module number and exact error number in this module
    Negative values - application level errors, encoded differently in every software stack, i.e. UIErrno

    code = <module><error number in module>

    <module> ::= <digit><digit>     -- module's code/number in two digits (table below)

    <error number in module> ::= <digit><digit>
        | error handler's number in this module
        | error number from lower level

    Registered modules in app:

        ---- Repositories ---
        10 - GitHubRepository

        ---- Stores, DAOs ----
        49 - start from here downwards

        ---- Network endpoints or calls/requests ----
        50 - GitHubEndpoint
 */

/**
 * Encodes error code from module number and error number
 */
fun errCode(module: Int, errorNumber: Int): Int {
    return module * 100 + errorNumber
}
