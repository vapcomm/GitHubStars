/*
 * (c) VAP Communications Group, 2023
 */

package online.vapcom.githubstars.network

import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.JsonConvertException
import kotlinx.serialization.SerializationException
import online.vapcom.githubstars.data.ErrorState
import online.vapcom.githubstars.data.UIErrno
import java.net.ConnectException
import java.net.ProtocolException
import java.net.UnknownHostException

/**
 * Translates ktor's Exceptions to meaningful error descriptions
 */
fun ktorExceptionToErrorState(url: String, ex: Exception, defaultErrorCode: Int): ErrorState {
    return when (ex) {
        // 300..399
        is RedirectResponseException -> ErrorState(UIErrno.SERVER_ERROR.errno, ex.response.status.toString())

        // 400..499 codes
        is ClientRequestException -> {
            val rsp = ex.response
            when (rsp.status.value) {
                401, 403 -> ErrorState(UIErrno.SESSION_CLOSED.errno, rsp.status.toString())
                422 -> ErrorState(UIErrno.CONTENT_ERROR.errno, rsp.status.toString())
                else -> ErrorState(UIErrno.CLIENT_ERROR.errno, rsp.status.toString())
            }
        }

        // 500..599
        is ServerResponseException -> ErrorState(UIErrno.SERVER_ERROR.errno, ex.response.status.toString())

        // all other ktor's errors
        is HttpRequestTimeoutException -> ErrorState(UIErrno.TIMEOUT.errno, "")
        is ResponseException -> ErrorState(UIErrno.SERVER_ERROR.errno, ex.response.status.toString())

        is ConnectTimeoutException -> ErrorState(UIErrno.CONNECTION_ERROR.errno, ex.message ?: "")
        is SocketTimeoutException -> ErrorState(UIErrno.TIMEOUT.errno, ex.message ?: "")

        // JSON parse errors
        is SerializationException, is JsonConvertException -> ErrorState(UIErrno.SERVER_ERROR.errno, ex.message ?: "")

        // otherwise try to decode system's dependant exceptions
        else -> systemNetworkExceptionToErrorState(url, ex, defaultErrorCode)
    }
}

/**
 * Translate system dependant network exceptions i.e. java.net.UnknownHostException to ErrorState
 */
private fun systemNetworkExceptionToErrorState(url: String, ex: Exception, defaultErrorCode: Int): ErrorState {
    return when (ex) {
        is UnknownHostException, // host not found in DNS
        is ConnectException -> { // network unreachable or Connection Refused
            ErrorState(UIErrno.CONNECTION_ERROR.errno, ex.message ?: "")
        }
        is java.net.SocketTimeoutException -> ErrorState(UIErrno.TIMEOUT.errno, ex.message ?: "")
        is ProtocolException -> {
            // it's may be "unexpected end of stream" error if provider shows HTML page on blocked site during TLS handshake
            ErrorState(UIErrno.SERVER_ERROR.errno, ex.message ?: "")
        }
        else -> ErrorState(defaultErrorCode, "Request to $url failed", ex.message ?: "", ex.stackTraceToString())
    }
    
}

/**
 * Translates HTTP error status codes to ErrorState.
 *
 * You should check response status before calling this function, it returns error on any HTTP codes.
 * ```
 * response.status.isSuccess()
 * ```
 */
fun httpStatusToErrorState(status: HttpStatusCode, moduleCode: Int): ErrorState {
    return when (status.value) {
        in 300..399 -> ErrorState(UIErrno.SERVER_ERROR.errno, "Server error: $moduleCode, $status")
        401, 403 -> ErrorState(UIErrno.SESSION_CLOSED.errno, status.toString())
        422 -> ErrorState(UIErrno.CONTENT_ERROR.errno, status.toString())
        in 400..499 -> ErrorState(UIErrno.CLIENT_ERROR.errno, status.toString())
        in 500..599 -> ErrorState(UIErrno.SERVER_ERROR.errno, "Server error: $moduleCode, $status")
        else -> ErrorState(UIErrno.CLIENT_ERROR.errno, status.toString())
    }
}
