/**
 * (c) VAP Communications Group, 2023
 */

package online.vapcom.githubstars.data

/**
 * Error codes in ErrorState to show errors, or do some actions on UI level.
 * Every UI screen may has some special actions for error recovery.
 *
 * NOTE: don't change errno codes, add a new one if it **really** needs, try to use some code with similar semantics.
 */
enum class UIErrno(val errno: Int) {
    // NO_ERROR(0),
    UNKNOWN_USER(-1),       // unknown user on login process
    AUTH_ERROR(-2),         // authentication error (unknown user or wrong password)
    CONNECTION_ERROR(-3),   // TCP connection establishment error, i.e. Connection Refused
    CLIENT_ERROR(-4),       // 400 HTTP errors
    SERVER_ERROR(-5),       // 500 HTTP errors
    TIMEOUT(-8),            // request timeout expired
    DATA_NOT_FOUND(-9),     // no data for response
    SESSION_CLOSED(-10),    // user not authenticated, or sid (accessToken) is expired
    INTERNAL_ERROR(-11),    // app's internal error, some misconfiguration of project
    ALREADY_EXISTS(-12),    // entity already exists
    ACCESS_DENIED(-14),     // access denied and/or forbidden

    DATA_ERROR(-28),        // any problems with data processing/encoding
    CONTENT_ERROR(-29),     // unable to get content due error
}
