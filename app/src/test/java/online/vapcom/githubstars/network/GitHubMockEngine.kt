/*
 * (c) VAP Communications Group, 2023
 */

package online.vapcom.githubstars.network

import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockRequestHandleScope
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.respondError
import io.ktor.client.request.HttpRequestData
import io.ktor.client.request.HttpResponseData
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.utils.io.ByteReadChannel

/**
 * Creates HttpClient mock engine for GitHubEndpoint tests
 */
fun makeGitHubMockEngine() = MockEngine { request ->
    when (request.method) {
        HttpMethod.Get -> {
            when (request.url.host) {
                "api" -> handleApiGetRequest(request)
                else -> respondError(HttpStatusCode.NotFound, "Host ${request.url.host} for GET request not found")
            }

        }

        else -> respondError(HttpStatusCode.MethodNotAllowed, "Method ${request.method.value} not supported")
    }
}

/**
 * Requests to api node
 */
private fun MockRequestHandleScope.handleApiGetRequest(request: HttpRequestData): HttpResponseData {
    println("|||| handleApiGetRequest: '${request.url.encodedPath}'")

    return when (request.url.encodedPath) {
        "/search/repositories" -> searchRepositoriesHandler(request)

        else -> respondError(HttpStatusCode.NotFound, "${request.url.encodedPath} not found")
    }

}

private fun MockRequestHandleScope.searchRepositoriesHandler(request: HttpRequestData): HttpResponseData {
    // use page param to separate test responses
    val page = request.url.parameters["page"] ?: ""
    println("|||| searchRepositoriesHandler: page: '$page'")

    return when (page) {
        "1" -> {
            respond(
                content = ByteReadChannel(COMPLETE_SEARCH_RESPONSE),
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        "2" -> {
            // not full search data, GitHub overloaded
            respond(
                content = ByteReadChannel(INCOMPLETE_SEARCH_RESPONSE),
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        "3" -> {
            // not seen, but possible, should deserialize to default values
            respond(
                content = ByteReadChannel("{}"),
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        "4" -> {
            // if asked to many stars in q=stars:>1 request params, GitHub returns empty result
            respond(
                content = ByteReadChannel("""{"total_count":0,"incomplete_results":false,"items":[]}"""),
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        else -> respond(
            status = HttpStatusCode.UnprocessableEntity,
            content = ByteReadChannel(
                """{"message": "Only the first 1000 search results are available", "documentation_url": "https://docs.github.com/v3/search/"}"""
            ),
            headers = headersOf(HttpHeaders.ContentType, "application/json")
        )
    }
}

private const val COMPLETE_SEARCH_RESPONSE = """{
  "total_count": 6065596,
  "incomplete_results": false,
  "items": [
    {
      "id": 2126244,
      "node_id": "MDEwOlJlcG9zaXRvcnkyMTI2MjQ0",
      "name": "bootstrap",
      "full_name": "twbs/bootstrap",
      "private": false,
      "owner": {
        "login": "twbs",
        "id": 2918581,
        "node_id": "MDEyOk9yZ2FuaXphdGlvbjI5MTg1ODE=",
        "avatar_url": "https://avatars.githubusercontent.com/u/2918581?v=4",
        "html_url": "https://github.com/twbs",
        "type": "Organization",
        "site_admin": false
      },
      "html_url": "https://github.com/twbs/bootstrap",
      "description": "The most popular HTML, CSS, and JavaScript framework for developing responsive, mobile first projects on the web.",
      "fork": false,
      "homepage": "https://getbootstrap.com",
      "size": 236554,
      "stargazers_count": 165809,
      "watchers_count": 165809,
      "language": "JavaScript",
      "forks_count": 79023,
      "mirror_url": null,
      "archived": false,
      "disabled": false,
      "open_issues_count": 502,
      "allow_forking": true,
      "is_template": false,
      "web_commit_signoff_required": false,
      "topics": [
        "bootstrap",
        "css",
        "css-framework",
        "html",
        "javascript",
        "sass",
        "scss"
      ],
      "visibility": "public",
      "forks": 79023,
      "open_issues": 502,
      "watchers": 165809,
      "default_branch": "main",
      "score": 1.0
    }
  ]
}
"""

private const val INCOMPLETE_SEARCH_RESPONSE = """{
  "total_count": 5979794,
  "incomplete_results": true,
  "items": [
    {
      "id": 177736533,
      "node_id": "MDEwOlJlcG9zaXRvcnkxNzc3MzY1MzM=",
      "name": "996.ICU",
      "full_name": "996icu/996.ICU",
      "private": false,
      "owner": {
        "login": "996icu",
        "id": 48942249,
        "node_id": "MDQ6VXNlcjQ4OTQyMjQ5",
        "avatar_url": "https://avatars.githubusercontent.com/u/48942249?v=4",
        "type": "User",
        "site_admin": false
      },
      "html_url": "https://github.com/996icu/996.ICU",
      "description": "Repo for counting stars and contributing. Press F to pay respect to glorious developers.",
      "fork": false,
      "size": 187836,
      "stargazers_count": 268127,
      "watchers_count": 268127,
      "language": null,
      "forks_count": 21479,
      "mirror_url": null,
      "archived": false,
      "disabled": false,
      "visibility": "public",
      "forks": 21479,
      "open_issues": 16711,
      "watchers": 268127,
      "default_branch": "master",
      "score": 1.0
    }
  ]
}
"""
